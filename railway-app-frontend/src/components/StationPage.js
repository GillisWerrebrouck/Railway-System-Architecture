import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class StationPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      station: [],
      isLoading: true
    };
  }

  componentDidMount() {
    endpoints.getStation(this.props.match.params.id)
      .then((result) => {
        this.setState({ station: result.data.platforms, isLoading: false });
    });
  }

  renderStation() {
        return this.state.station.map((reservedSlots, index) => {
            const { platformId, timetableId, arrivalDateTime, departureDateTime, delayInMinutes } = reservedSlots;
            return (
                <tr key={platformId}>
                    <td>{platformId}</td>
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
       <h2>Station</h2>
        {!this.state.isLoading ? (
         <table id='station'>
          <tbody>
           <tr>
            <th>Platform ID</th>
            <th>Timetable ID</th>
            <th>Arrival DateTime</th>
            <th>Departure DateTime</th>
            <th>Delay (minutes)</th>
           </tr>
          { this.renderStation() }
         </tbody>
        </table>
        ) : (
        <p>Loading...</p>
       )}
      </div>
     );
    }
}
