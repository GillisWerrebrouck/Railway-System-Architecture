import React, { Component } from 'react';
import endpoints from '../api/endpoints';
import { NEO4J_URI, NEO4J_USER, NEO4J_PASSWORD } from '../api/constants'
import { ResponsiveNeoGraph } from "./NeoGraph";

export default class NetworkPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isRoutesLoading: true,
      isStationsLoading: true,
      routes: [],
      stations: [],
    };
  }

  componentDidMount() {
    this.getRoutes();
    this.getStations();
  }

  getStations() {
    endpoints.getStations()
      .then((result) => {
        this.setState({ stations: result.data, isStationsLoading: false });
      });
  }

  getRoutes() {
    endpoints.getRoutes()
    .then((result) => {
      this.setState({ routes: result.data });

      this.state.routes.forEach((route, routeIndex) => {
        endpoints.getStationsOnRoute(route.id)
          .then((result) => {
            let stations = [];
            result.data.forEach((station) => { 
              stations.push(station.name);
            });
            return stations;
          })
          .then((stations) => {
            endpoints.getConnectionsOnRoute(route.id)
              .then((result) => {
                const connections = result.data;
                
                this.state.routes[routeIndex].stations = stations[0];
                connections.forEach((connection, connectionIndex) => {
                  this.state.routes[routeIndex].stations += " -- " + connection.distance + " --> " + stations[connectionIndex+1];
                });
                this.setState({ isRoutesLoading: false });
              });
          });
      });
    });
  }

  renderRoutes() {
    return this.state.routes.map((route, index) => {
      const { id, name, stations } = route;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{name}</td>
          <td>{stations}</td>
        </tr>
      );
    });
  }

  renderStations() {
    return this.state.stations.map((station, index) => {
      const { id, name, address, platforms } = station;
      const { street, city, province, country } = address;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{name}</td>
          <td>{street}</td>
          <td>{city}</td>
          <td>{province}</td>
          <td>{country}</td>
          <td>{platforms.length}</td>
        </tr>
      );
    });
  }

  render() {
    return (
      <div>
        <h2>Network</h2>
        <ResponsiveNeoGraph
          containerId={"network-graph"}
          neo4jUri={NEO4J_URI}
          neo4jUser={NEO4J_USER}
          neo4jPassword={NEO4J_PASSWORD}
        />

        <h3>Routes</h3>
        {!this.state.isRoutesLoading ? (
        <table id='routes'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Stations - distance in km</th>
            </tr>
            { this.renderRoutes() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}

        <h3>Stations</h3>
        {!this.state.isStationsLoading ? (
        <table id='stations'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Street</th>
              <th>City</th>
              <th>Province</th>
              <th>Country</th>
              <th>#Platforms</th>
            </tr>
            { this.renderStations() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}
      </div>
    );
  }
}
