import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class TrainPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      trains: [],
    };
  }

  componentDidMount() {
    endpoints.getTrains()
      .then((result) => {
        this.setState({ trains: result.data, isLoading: false });
      });
  }

  renderTrains() {
    return this.state.trains.map((train, index) => {
      const { id, status, type, groupCapacity, totalCapacity, technicaldetails } = train;
      const { fuel, lastCheck } = technicaldetails;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{status}</td>
          <td>{type}</td>
          <td>{groupCapacity} - {totalCapacity}</td>
          <td>{fuel}</td>
          <td>{lastCheck}</td>
        </tr>
      );
    });
  }

  render() {
    return (
      <div>
        <h2>Train</h2>

        {!this.state.isLoading ? (
        <table id='trains'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Status</th>
              <th>Type</th>
              <th>Groupcapacity - Totalcapacity</th>
              <th>Fuel type</th>
              <th>Last checked</th>
            </tr>
            { this.renderTrains() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}
      </div>
    );
  }
}
