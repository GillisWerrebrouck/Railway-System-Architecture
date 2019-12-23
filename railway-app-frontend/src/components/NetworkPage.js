import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class NetworkPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      stations: [],
    };
  }

  componentDidMount() {
    endpoints.getStations()
      .then((result) => {
        this.setState({ stations: result.data, isLoading: false });
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

        <h3>Stations</h3>
        {!this.state.isLoading ? (
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
