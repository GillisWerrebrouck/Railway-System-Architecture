import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class StationSchedulePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      station: [],
      isLoading: true,
    };
  }

  componentDidMount() {
    endpoints.getStation(this.props.match.params.id)
      .then((result) => {
        this.setState({ station: result.data, isLoading: false });
    });
  }

  renderPlatformSchedule(reservedSlots) {
    return reservedSlots.map((scheduleItem) => {
      const { id, timetableId, arrivalDateTime, departureDateTime, delayInMinutes } = scheduleItem;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{timetableId}</td>
          <td>{arrivalDateTime}</td>
          <td>{departureDateTime}</td>
          <td>{delayInMinutes}</td>
        </tr>
      );
    });
  }

  render() {
    return (
    <div>
      <h2>Station { this.state.station.name }</h2>
      {!this.state.isLoading ? (
        <div>
          <h2>Address</h2>
          <ul>
            <li>{ this.state.station.address.street }</li>
            <li>{ this.state.station.address.city }</li>
            <li>{ this.state.station.address.province }</li>
            <li>{ this.state.station.address.country }</li>
          </ul>

          <h2>Platforms</h2>
          {
            this.state.station.platforms.map((platform) => {
              return (<div key={platform.id}>
                <h3>Platform {platform.platformNumber}</h3>

                <table className='platform'>
                  <tbody>
                    <tr>
                      <th>ID</th>
                      <th>Timetable ID</th>
                      <th>Arrival datetime</th>
                      <th>Departure datetime</th>
                      <th>Delay (in minutes)</th>
                    </tr>
                  { this.renderPlatformSchedule(platform.reservedSlots) }
                  </tbody>
                </table>
              </div>)
            })
          }
        </div>
      ) : (
      <p>Loading...</p>
      )}
    </div>
    );
  }
}
