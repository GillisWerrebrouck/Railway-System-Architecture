import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import endpoints from '../api/endpoints'; 

export default class TrainPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      trains: [],
      changeStatusRequest:{
      trainId: null,
      status: "ACTIVE",
          },
          train: {
      id: null,
            status: "ACTIVE",
      type: "IC",
      totalCapacity: 0,
      groupCapacity: 0,
      technicaldetails: {
              fuel: "ELECTRIC",
              lastCheck: null,
              defects: null,
            },
      scheduleItems: null
          },
          maintenanceRequest: {
      trainId: null,
      maintenanceMessage: null,
      maintenanceDate: null,
          },
          accidentRequest: {
      timetableId: null,
      trainId: null,
      accidentMessage: null,
      emergencyServicesRequired: false,
      }
    };
  }

  componentDidMount() {
    endpoints.getTrains()
      .then((result) => {
        this.setState({ trains: result.data, isLoading: false });
      });
  }

  trainFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let train = {...this.state.train};

    if(name === "fuel" || name === "lastCheck"){
	train["technicaldetails"][name] = value;
    } else{
	train[name] = value;
    }
    this.setState({ train });
  }

statusFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let changeStatusRequest = {...this.state.changeStatusRequest};
    changeStatusRequest[name] = value;
    
    this.setState({ changeStatusRequest });
  }

maintenanceFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let maintenanceRequest = {...this.state.maintenanceRequest};
    maintenanceRequest[name] = value;
    
    this.setState({ maintenanceRequest });
  }

  changeStatus = (event) => {
    event.preventDefault();
    endpoints.postStatus(this.state.train)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data }); 
        } else {
          window.location.reload();
        }
      });
  }

accidentFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let accidentRequest = {...this.state.accidentRequest};
    accidentRequest[name] = value;
    
    this.setState({ accidentRequest });
  }

  changeStatus = (event) => {
    event.preventDefault();
    endpoints.postStatus(this.state.changeStatusRequest)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data }); 
        } else {
          window.location.reload();
        }
      });
  }

addTrain = (event) => {
    event.preventDefault();
    endpoints.postNewTrain(this.state.train)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data });
        } else {
          window.location.reload();
        }
      });
  }

requestMaintenance = (event) => {
    event.preventDefault();
    endpoints.postNewMaintenanceRequest(this.state.maintenanceRequest)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data });
        } else {
          window.location.reload();
        }
      });
  }

notifyAccident = (event) => {
    event.preventDefault();
    endpoints.postNewAccidentRequest(this.state.accidentRequest)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data }); 
        } else {
          window.location.reload();
        }
      });
  }

  renderTrains() {
    return this.state.trains.map((train, index) => {
      const { id, status, type, totalCapacity, groupCapacity, technicaldetails } = train;
      const { fuel, lastCheck } = technicaldetails;	
      return (
        <tr key={id}>
          <td><Link to={{pathname: `/train/${id}`}}>{id}</Link></td>
          <td>{status}</td>
          <td>{type}</td>
          <td>{groupCapacity} - {totalCapacity}</td>
          <td>{fuel ? fuel : ""}</td>
          <td>{lastCheck ? lastCheck : ""}</td>
        </tr>
      );
    });
  }

  render() {
    return (
      <div>
        <h2>Train</h2>

        {!this.state.isLoading ? (
        <table id='trains'>
          <tbody>
            <tr>
	            <th>ID</th>
              <th>Status</th>
              <th>Type</th>
              <th>Groupcapacity - Totalcapacity</th>
              <th>Fuel type</th>
              <th>Last checked</th>
            </tr>
            { this.renderTrains() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}

	      <h3>Set train active/non-active</h3>
        <form onSubmit={this.changeStatus}>
          <label>Train ID: </label>
          <input
            type='text'
            name='trainId'
            onChange={this.statusFormChangeHandler}
          />
          <br />
          <label>Train status: </label>
          <select name='status' onChange={this.statusFormChangeHandler}>
            <option value="ACTIVE">ACTIVE</option>
            <option value="NONACTIVE">NONACTIVE</option>
          </select>
          <br />
          <input
            type='submit'
            value='CHANGE STATUS'
          />
        </form>

        <h3>Add train</h3>
        <form onSubmit={this.addTrain}>
          <label>Train type: </label>
          <select name='type' onChange={this.trainFormChangeHandler}>
            <option value="IC">IC</option>
            <option value="IR">IR</option>
	          <option value="P">P</option>
          </select>
          <br />

          <label>Total capacity: </label>
          <input
            type='number'
            name='totalCapacity'
            onChange={this.trainFormChangeHandler}
          />
	        <br/>

          <label>Total groupcapacity: </label>
          <input
            type='number'
            name='groupCapacity'
            onChange={this.trainFormChangeHandler}
          />
	        <br/>

          <label>Train status: </label>
          <select name='status' onChange={this.trainFormChangeHandler}>
            <option value="ACTIVE">ACTIVE</option>
            <option value="NONACTIVE">NONACTIVE</option>
          </select>
          <br />

          <label>Train fuel: </label>
          <select name='fuel' onChange={this.trainFormChangeHandler}>
            <option value="ELECTRIC">ELECTRIC</option>
            <option value="DIESEL">DIESEL</option>
   	        <option value="HYBRID">HYBRID</option>
          </select>
          <br />

          <label>Last check: </label>
          <input
            type='text'
            name='lastCheck'
            onChange={this.trainFormChangeHandler}
          /> <span>&nbsp;format: yyyy-mm-ddThh:mm:00.000</span>
	        <br/>

          <input
            type='submit'
            value='ADD TRAIN'
          />
	      </form>

	      <h3>Request maintenance</h3>
        <form onSubmit={this.requestMaintenance}>
          <label>Train ID: </label>
          <input
            type='text'
            name='trainId'
            onChange={this.maintenanceFormChangeHandler}
          />
          <br />

          <label>Message: </label>
          <input
            type='text'
            name='maintenanceMessage'
            onChange={this.maintenanceFormChangeHandler}
          />
	        <br/>

          <label>Date: </label>
          <input
            type='text'
            name='maintenanceDate'
            onChange={this.maintenanceFormChangeHandler}
          />
	        <br/>

          <input
            type='submit'
            value='REQUEST'
          />
        </form>

	      <h3>Notify Accident</h3>
          <form onSubmit={this.notifyAccident}>
          <label>Timetable ID: </label>
          <input
            type='number'
            name='timetableId'
            onChange={this.accidentFormChangeHandler}
          />
          <br />

          <label>Train ID: </label>
          <input
            type='text'
            name='trainId'
            onChange={this.accidentFormChangeHandler}
          />
          <br />

          <label>Message: </label>
          <input
            type='text'
            name='accidentMessage'
            onChange={this.accidentFormChangeHandler}
          />
	        <br/>

          <label>Emergency services: </label>
          <select name='emergencyServiceRequired' onChange={this.accidentFormChangeHandler}>
            <option value="true">yes</option>
            <option value="false">no</option>
          </select>
          <br />
          <input
            type='submit'
            value='NOTIFY'
          />
        </form>
      </div>
    );
  }
}
