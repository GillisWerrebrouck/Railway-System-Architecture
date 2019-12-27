import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class TimetablePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      timetable: [],
      routes: [],
      newTimetableItem: {
        routeId: null,
        startDateTime: null,
        requestedTrainType: "IR",
        amountOfTrainConductors: null,
      },
      createTimetableItemErrorResponse: "",
    };
  }

  componentDidMount() {
    endpoints.getTimetable()
      .then((result) => {
        this.setState({ timetable: result.data, isLoading: false });
      });
    this.getRoutes();
  }

  getRoutes() {
    endpoints.getRoutes()
    .then((result) => {
      this.setState({ routes: result.data });
    });
  }

  renderTimetable() {
    return this.state.timetable.map((timetableItem, index) => {
      const { id, route, routeId, startDateTime, endDateTime, delay } = timetableItem;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{route}</td>
          <td>{routeId}</td>
          <td>{startDateTime}</td>
          <td>{endDateTime}</td>
          <td>{delay}</td>
        </tr>
      );
    });
  }

  createTimetableItemFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let newTimetableItem = {...this.state.newTimetableItem};
    newTimetableItem[name] = value;
    this.setState({ newTimetableItem });
  }

  createTimetableItem = (event) => {
    event.preventDefault();
    endpoints.postNewTimetableItem(this.state.newTimetableItem)
      .then(() => { window.location.reload(); })
      .catch((error) => {
        this.setState({ createTimetableItemErrorResponse: typeof error === "object" ? "Failed to create timetable item, check all input fields" : error });
      });
  }

  render() {
    return (
      <div>
        <h2>Timetable</h2>

        {!this.state.isLoading ? (
        <table id='timetable'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Route name</th>
              <th>Route ID</th>
              <th>Start</th>
              <th>End</th>
              <th>Delay (minutes)</th>
            </tr>
            { this.renderTimetable() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}

        <h3>Create a timetable item</h3>
        <form onSubmit={this.createTimetableItem}>
          <label>Route ID: </label>
          <select name='routeId' onChange={this.createTimetableItemFormChangeHandler}>
            <option value="">choose a route</option>
            {this.state.routes.map((route) => {
              return (<option key={route.id} value={route.id}>{route.id + ": " + route.name}</option>)
            })}
          </select>
          <br />

          <label>Start datetime: </label>
          <input
            type='datetime'
            name='startDateTime'
            onChange={this.createTimetableItemFormChangeHandler}
          />
          <span>&nbsp;format: yyyy-mm-ddThh:mm:00.000</span>
          <br />

          <label>Train type: </label>
          <select name='requestedTrainType' onChange={this.createTimetableItemFormChangeHandler}>
            <option value="IR">IR</option>
            <option value="IC">IC</option>
            <option value="P">P</option>
          </select>
          <br />

          <label>#Train conductors: </label>
          <input
            type='number'
            name='amountOfTrainConductors'
            onChange={this.createTimetableItemFormChangeHandler}
          />
          <br />

          <input
            type='submit'
            value='CREATE'
          />
        </form>

        <p>{this.state.createTimetableItemErrorResponse}</p>
      </div>
    );
  }
}
