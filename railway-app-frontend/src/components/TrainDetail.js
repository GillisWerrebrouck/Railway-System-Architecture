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
    return this.state.train.scheduleItems.map((s, index) => {
      const { timetableId, reservationType, startDateTime, endDateTime} = s;	
      return (
        <tr key={timetableId}>
          <td>{timetableId}</td>
          <td>{reservationType}</td>
          <td>{startDateTime}</td>
	  <td>{endDateTime}</td>
        </tr>
      );
    });
  }

  render() {
    return (
      <div>
        <h2>Schedule items from train: {this.props.match.params.id}</h2>
	{!this.state.isLoading ? (
        <table id='scheduleItem'>
          <tbody>
            <tr>
	      <th>timetableId</th>
              <th>reservationType</th>
              <th>startDateTime</th>
              <th>endDateTime</th>
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
