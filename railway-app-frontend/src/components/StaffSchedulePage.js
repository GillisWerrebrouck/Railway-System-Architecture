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
        return this.state.schedules.map((id, requestId, startDate, endDate) => {
            return (
                <tr key={id}>
                    <td>{id}</td>
                    <td>{requestId}</td>
                    <td>{startDate} - {endDate}</td>
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
            <th>ID</th>
            <th>Request ID</th>
            <th>Date</th>
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
