import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class TimetablePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      timetable: [],
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
      </div>
    );
  }
}
