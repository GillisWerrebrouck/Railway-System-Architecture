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
    endpoints.getMaintenanceSchedules()
      .then((result) => {
        this.setState({ maintenance: result.data, isLoading: false });
      });
  }

  changeStatus = (event) => {
    event.preventDefault();

    const status = event.target.name;
    const id = event.target.value;

    endpoints.putChangeMaintenanceStatus(id, status)
      .then((result) => { window.location.reload(); });
  }

  renderMaintenance() {
    return this.state.maintenance.map((scheduleItem) => {
      const { id, startDate, endDate, staffIds, requestId, staffReservationMessage, status, maintenanceMessage, trainId, maintenanceType } = scheduleItem;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{startDate} - {endDate}</td>
          <td>{staffIds.join(', ')}</td>
          <td>{staffReservationMessage ? staffReservationMessage : '---'}</td>
          <td>{status}</td>
          <td>{maintenanceMessage}</td>
          <td>{trainId}</td>
          <td>{maintenanceType}</td>
          <td>
            <button name='SCHEDULED' value={id} onClick={this.changeStatus}>SCHEDULED</button>
            <button name='FINISHED' value={id} onClick={this.changeStatus}>FINISHED</button>
            <button name='STARTED' value={id} onClick={this.changeStatus}>STARTED</button>
            <button name='UNFINISHED' value={id} onClick={this.changeStatus}>UNFINISHED</button>
          </td>
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
                <th>Start date - end date</th>
                <th>Staff IDs</th>
                <th>Staff Reservation Message</th>
                <th>Status</th>
                <th>Message</th>
                <th>Train ID</th>
                <th>Maintenance type</th>
                <th>Change status</th>
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
