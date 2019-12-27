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
        return this.state.schedules.map((scheduleItem, index) => {
        const { id, requestId, startDate, endDate } = scheduleItem;
        return (
                <tr key={id}>
                    <td>{id}</td>
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
