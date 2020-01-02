import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class TrainDetail extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      train: {
	id: null,
        status: "ACTIVE",
	type: null,
	totalCapacity: 0,
	groupCapacity: 0,
	technicaldetails: {
           fuel: null,
           lastCheck: null,
           defects: null,
        },
	scheduleItems: null
      }
    };
  }

  componentDidMount() {
    endpoints.getTrain(this.props.match.params.id)
      .then((result) => {
        this.setState({ train: result.data, isLoading: false })
      });
  }

  renderScheduleItems() {
    if (this.state.train.scheduleItems != null){
      return this.state.train.scheduleItems.map((scheduleItem) => {
        const { timetableId, reservationType, startDateTime, endDateTime} = scheduleItem;	
        return (
          <tr key={timetableId}>
            <td>{timetableId ? timetableId : '---'}</td>
            <td>{reservationType}</td>
            <td>{startDateTime}</td>
            <td>{endDateTime}</td>
          </tr>
        );
      });
    } else {
      return null;
    }
  }

  render() {
    return (
      <div>
        <h2>Schedule items for train: {this.props.match.params.id}</h2>
	      {!this.state.isLoading ? (
          <table id='scheduleItem'>
            <tbody>
              <tr>
                <th>Timetable ID</th>
                <th>Reservation type</th>
                <th>Start datetime</th>
                <th>End datetime</th>
              </tr>
              { this.renderScheduleItems() }
            </tbody>
          </table>
        ) : (
          <p>Loading...</p>
        )}
      </div>
    );
  }
}
