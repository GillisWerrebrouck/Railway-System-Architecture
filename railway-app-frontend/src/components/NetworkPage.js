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
      createConnection: {
        stationXId: null,
        stationYId: null,
        distance: 0,
        maxSpeed: 0,
        active: "true",
      },
      createConnectionErrorResponse: null, 
      changeConnectionState: {
        id: null,
        active: "true",
      },
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

                let routes = this.state.routes;
                routes[routeIndex].stations = stations[0];

                connections.forEach((connection, connectionIndex) => {
                  routes[routeIndex].stations += " -- " + connection.distance + " --> " + stations[connectionIndex+1];
                });
                this.setState({ routes });
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

  changeCreateConnectionFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let createConnection = {...this.state.createConnection};
    createConnection[name] = value;
    this.setState({ createConnection });
  }

  changeConnectionStateFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let changeConnectionState = {...this.state.changeConnectionState};
    changeConnectionState[name] = value;
    this.setState({ changeConnectionState });
  }

  createConnection = (event) => {
    event.preventDefault();

    let createConnection = {...this.state.createConnection};
    createConnection.active = createConnection.active === "true";
    this.setState({ createConnection });

    endpoints.postCreateConnection(this.state.createConnection)
      .then(() => { window.location.reload(); })
      .catch(() => {
        this.setState({ createConnectionErrorResponse: "Both fields are mandatory, the 2 stations also need to be different." });
      });
  }

  changeConnectionState = (event) => {
    event.preventDefault();

    let changeConnectionState = {...this.state.changeConnectionState};
    changeConnectionState.active = changeConnectionState.active === "true";
    this.setState({ changeConnectionState });

    endpoints.putChangeConnectionState(this.state.changeConnectionState)
      .then(() => { window.location.reload(); });
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

        <h3>Create connection between two stations</h3>
        <form onSubmit={this.createConnection}>
          <label>Station x: </label>
          <select name='stationXId' onChange={this.changeCreateConnectionFormChangeHandler}>
            <option value="">choose a station</option>
            {this.state.stations.map((station) => {
              return (<option key={station.id} value={station.id}>{station.name}</option>)
            })}
          </select>
          <br />

          <label>Station y: </label>
          <select name='stationYId' onChange={this.changeCreateConnectionFormChangeHandler}>
            <option value="">choose a station</option>
            {this.state.stations.map((station) => {
              return (<option key={station.id} value={station.id}>{station.name}</option>)
            })}
          </select>
          <br />

          <label>Distance (in km): </label>
          <input
            type='number'
            name='distance'
            placeholder='0'
            onChange={this.changeCreateConnectionFormChangeHandler}
          />
          <br />

          <label>Max speed (in km/h): </label>
          <input
            type='number'
            name='maxSpeed'
            placeholder='0'
            onChange={this.changeCreateConnectionFormChangeHandler}
          />
          <br />

          <label>Status: </label>
          <select name='active' onChange={this.changeCreateConnectionFormChangeHandler}>
            <option value="true">active</option>
            <option value="false">non-active</option>
          </select>
          <br />

          <input
            type='submit'
            value='CREATE'
          />
        </form>
        <p>{this.state.createConnectionErrorResponse}</p>

        <h3>Change connection status</h3>
        <p>
          A connection between two stations has a status (active/non-active). 
          Only connections that aren't used in any timetable items (from datetime now) can be set inactive.
        </p>
        <form onSubmit={this.changeConnectionState}>
          <label>Connection ID: </label>
          <input
            type='number'
            name='id'
            onChange={this.changeConnectionStateFormChangeHandler}
          />
          <br />

          <label>Status: </label>
          <select name='active' onChange={this.changeConnectionStateFormChangeHandler}>
            <option value="true">active</option>
            <option value="false">non-active</option>
          </select>
          <br />

          <input
            type='submit'
            value='SET'
          />
        </form>
      </div>
    );
  }
}
