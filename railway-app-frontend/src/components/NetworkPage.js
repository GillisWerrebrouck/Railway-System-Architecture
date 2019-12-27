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
      isConnectionsLoading: true,
      routes: [],
      connections: [],
      stations: [],
      routeServiceStations: [],
      createRoute: {
        name: null,
        startStation: null,
      },
      routeParts: [
        {
          station: "",
          connectionId: "",
        }
      ],
      createConnection: {
        stationXId: null,
        stationYId: null,
        distance: 0,
        maxSpeed: 0,
        active: "true",
      },
      createRouteErrorResponse: null,
      createConnectionErrorResponse: null, 
      changeConnectionState: {
        id: null,
        active: "true",
      },
    };
  }

  componentDidMount() {
    this.getRoutes();
    this.getConnections();
    this.getStations();
    this.getStationsFromRouteService();
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

  getConnections() {
    endpoints.getConnections()
      .then((result) => {
        this.setState({ connections: result.data.sort((a, b) => a.id > b.id), isConnectionsLoading: false });
      });
  }

  getStations() {
    endpoints.getStations()
      .then((result) => {
        this.setState({ stations: result.data, isStationsLoading: false });
      });
  }

  getStationsFromRouteService() {
    endpoints.getStationsFromRouteService()
      .then((result) => {
        this.setState({ routeServiceStations: result.data });
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

  renderConnections() {
    return this.state.connections.map((connection, index) => {
      const { id, stationX, stationY, distance, maxSpeed, active } = connection;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{stationX.name}</td>
          <td>{stationY.name}</td>
          <td>{distance}</td>
          <td>{maxSpeed}</td>
          <td>{active ? "active" : "non-active"}</td>
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

  addStationToRoute = (e) => {
    this.setState((prevState) => ({
      routeParts: [
        ...prevState.routeParts,
        {
          station: "",
          connectionId: "",
        }
      ],
    }));
  }

  createRouteFormPartChangeHandler = (event) => {
    if (["station", "connectionId"].includes(event.target.className) ) {
      let routeParts = [...this.state.routeParts]   
      routeParts[event.target.dataset.id][event.target.className] = event.target.value
    } else {
      this.setState({ [event.target.name]: event.target.value })
    }
  }

  createRouteFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let createRoute = {...this.state.createRoute};
    createRoute[name] = value;
    this.setState({ createRoute });
  }

  createConnectionFormChangeHandler = (event) => {
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

  createRoute = (event) => {
    event.preventDefault();

    if(this.state.createRoute.name === null) {
      this.setState({ createRouteErrorResponse: "No route name given" });
      return;
    } else if(!this.state.routeServiceStations.map(s => s.id.toString()).includes(this.state.createRoute.startStation)) {
      this.setState({ createRouteErrorResponse: "No start station chosen" });
      return;
    } else if(!this.state.connections.map(c => c.id.toString()).includes(this.state.routeParts[0].connectionId)) {
      this.setState({ createRouteErrorResponse: "No connection chosen for connection part 1" });
      return;
    }

    let route = {
      name: this.state.createRoute.name,
      routeConnections: [
        {
          station: {
            id: this.state.createRoute.startStation,
          },
          connectionId: this.state.routeParts[0].connectionId,
          startOfRoute: true,
        }
      ],
    };

    let failed = false;
    this.state.routeParts.forEach((routePart, i) => {
      if(!this.state.connections.map(c => c.id.toString()).includes(this.state.routeParts[i].connectionId)){
        this.setState({ createRouteErrorResponse: `No connection chosen for connection ${i+1}` });
        failed = true;
        return;
      }
      if(!this.state.routeServiceStations.map(s => s.id.toString()).includes(routePart.station)){
        this.setState({ createRouteErrorResponse: `No station chosen for route part ${i}` });
        failed = true;
        return;
      }
      route.routeConnections[i+1] = {
        station: {
          id: routePart.station,
        },
        connectionId: this.state.routeParts.length === i+1 ? null : this.state.routeParts[i+1].connectionId,
        startOfRoute: false,
      }
    });

    if(!failed) {
      endpoints.postCreateRoute(route)
        .then(() => { window.location.reload(); })
        .catch((error) => {
          this.setState({ createRouteErrorResponse: "All fields need to be filled out." });
        });
    }
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

        <p>
          This is the railway network, it consists out of station nodes and route nodes. Station nodes are connected with other station nodes. 
          Route nodes are connected to station nodes. The route-station relationship has a property "connectionId", this indicates the connection 
          (station-station) that will be used for that route. All route-station relationships have this property except for the last station on a route.
        </p>

        <ResponsiveNeoGraph
          containerId={"network-graph"}
          neo4jUri={NEO4J_URI}
          neo4jUser={NEO4J_USER}
          neo4jPassword={NEO4J_PASSWORD}
        />

        <h3>Routes</h3>

        <p>
          These are the routes that can be used for timetable items. They exist because travellers can only take certain routes on the network.
        </p>

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

        <h3>Connections</h3>

        <p>
          These are the connections between stations. Two stations can have multiple connections between them. These connections are bidirectional so it 
          is possible to travel from station x to station y and also the other way around. The connectionId property on route relationships decides what 
          connection between two stations should be used for that particular route.
        </p>

        {!this.state.isConnectionsLoading ? (
        <table id='connections'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Station X</th>
              <th>Station Y</th>
              <th>Distance (km)</th>
              <th>Max speed (km/h)</th>
              <th>Active</th>
            </tr>
            { this.renderConnections() }
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

        <h3>Create route</h3>
        <form onSubmit={this.createRoute}>
          <label>Route name: </label>
          <input
            type='string'
            name='name'
            placeholder='Station x - Station y'
            onChange={this.createRouteFormChangeHandler}
          />
          <br />

          <label>Station: </label>
          <select name='startStation' onChange={this.createRouteFormChangeHandler}>
            <option value="">choose a station</option>
            {this.state.routeServiceStations.map((station) => {
              return (<option key={station.id} value={station.id}>{station.name}</option>)
            })}
          </select>
          <br />

          <div id="routeStationsFormPart">
            {
              this.state.routeParts.map((routePart, i) => {
                return (
                  <div key={i}>
                    <label>Connection ID: </label>
                    <select name={i} data-id={i} className='connectionId' onChange={this.createRouteFormPartChangeHandler}>
                      <option value="">choose a connection</option>
                      {this.state.connections.map((connection) => {
                        return (<option key={connection.id} value={connection.id}>{connection.id + ": " + connection.stationX.name + " - " + connection.stationY.name}</option>)
                      })}
                    </select>
                    <br />

                    <label>Station: </label>
                    <select name={i} data-id={i} className='station' onChange={this.createRouteFormPartChangeHandler}>
                      <option value="">choose a station</option>
                      {this.state.routeServiceStations.map((station) => {
                        return (<option key={station.id} value={station.id}>{station.name}</option>)
                      })}
                    </select>
                    <br />
                  </div>
                );
              })
            }
          </div>

          <button onClick={this.addStationToRoute}>ADD STATION</button>
          <input
            type='submit'
            value='CREATE'
          />
        </form>
        <p>{this.state.createRouteErrorResponse}</p>

        <h3>Create connection between two stations</h3>
        <form onSubmit={this.createConnection}>
          <label>Station x: </label>
          <select name='stationXId' onChange={this.createConnectionFormChangeHandler}>
            <option value="">choose a station</option>
            {this.state.stations.map((station) => {
              return (<option key={station.id} value={station.id}>{station.name}</option>)
            })}
          </select>
          <br />

          <label>Station y: </label>
          <select name='stationYId' onChange={this.createConnectionFormChangeHandler}>
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
            onChange={this.createConnectionFormChangeHandler}
          />
          <br />

          <label>Max speed (in km/h): </label>
          <input
            type='number'
            name='maxSpeed'
            placeholder='0'
            onChange={this.createConnectionFormChangeHandler}
          />
          <br />

          <label>Status: </label>
          <select name='active' onChange={this.createConnectionFormChangeHandler}>
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
          <select name='id' onChange={this.changeConnectionStateFormChangeHandler}>
            <option value="">choose a connection</option>
            {this.state.connections.map((connection) => {
              return (<option key={connection.id} value={connection.id}>{connection.id + ": " + connection.stationX.name + " - " + connection.stationY.name}</option>)
            })}
          </select>
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
