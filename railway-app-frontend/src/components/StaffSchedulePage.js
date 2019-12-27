import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class StaffSchedulePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      schedules: [],
      isLoading: true
    };
  }

  componentDidMount() {
    endpoints.getStaffSchedule(this.props.match.params.id)
      .then((result) => {
        this.setState({ schedules: result.data.scheduleItems, isLoading: false });
    });
  }

  renderStaff() {
        return this.state.schedules.map((platformId, timetableId, arrivalDateTime, departureDateTime, delayInMinutes) => {
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
       <h2>Staff Schedules</h2>
        {!this.state.isLoading ? (
         <table id='staff'>
          <tbody>
           <tr>
            <th>Platform ID</th>
            <th>Timetable ID</th>
            <th>Arrival DateTime</th>
            <th>Departure DateTime</th>
            <th>Delay (minutes)</th>
           </tr>
          { this.renderStaff() }
         </tbody>
        </table>
        ) : (
        <p>Loading...</p>
       )}
      </div>
     );
    }
}
