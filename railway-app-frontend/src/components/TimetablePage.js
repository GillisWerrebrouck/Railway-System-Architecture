import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class TimetablePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      timetable: [],
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
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createTimetableItemErrorResponse: result.data });
        } else {
          window.location.reload();
        }
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
          <input
            type='number'
            name='routeId'
            onChange={this.createTimetableItemFormChangeHandler}
          />
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
