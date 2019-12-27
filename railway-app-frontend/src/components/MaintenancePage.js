import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class MaintenancePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      maintenance: []
    };
  }

  componentDidMount() {
    let array = [];
    endpoints.getMaintenanceSchedules()
      .then((result) => {
        result.data.forEach((item) => {
            array.push(item);
        });
        this.setState({ maintenance: array, isLoading: false });
      });
  }

  renderMaintenance() {
    return this.state.maintenance.map((Object) => {
    const { id, startDate, endDate, staffIds, requestId, staffReservationMessage, status, maintenanceMessage, trainId, maintenanceType } = Object;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{startDate} - {endDate}</td>
          <td>{String(staffIds.join(' '))}</td>
          <td>{String(requestId ? (requestId) : (''))}</td>
          <td>{String(staffReservationMessage ? (staffReservationMessage) : (''))}</td>
          <td>{String(status)}</td>
          <td>{String(maintenanceMessage)}</td>
          <td>{String(trainId)}</td>
          <td>{String(maintenanceType)}</td>
        </tr>
      );
     });
  }

  render() {
    return (
      <div>
        <h2>Maintenance</h2>
         {!this.state.isLoading ? (
           <table id='trains'>
             <tbody>
              <tr>
                <th>ID</th>
                <th>Start Date -> End Date</th>
                <th>Staff ID</th>
                <th>Request ID</th>
                <th>Staff Reservation Message</th>
                <th>Status</th>
                <th>Message</th>
                <th>Train ID</th>
                <th>Maintenance Type</th>
              </tr>
              { this.renderMaintenance() }
             </tbody>
           </table>
           ) : (
          <p>Loading...</p>
         )}
      </div>
    );
  }
}
