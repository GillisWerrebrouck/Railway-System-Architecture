import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class TrainPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      trains: [],
      Train: {
	trainId: null,
        status: "ACTIVE",
      },
    };
  }

  componentDidMount() {
    endpoints.getTrains()
      .then((result) => {
        this.setState({ trains: result.data, isLoading: false });
      });
  }

  statusFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let train = {...this.state.Train};
    train[name] = value;
    this.setState({ train });
  }

  changeStatus = (event) => {
    event.preventDefault();
    endpoints.postStatus(this.state.Train)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data });
	  console.log(result.data); 
        } else {
	  console.log("gelukt!"); 
          window.location.reload();
        }
      });
  }

  renderTrains() {
    return this.state.trains.map((train, index) => {
      const { id, status, type, groupCapacity, totalCapacity, technicaldetails } = train;
      const { fuel, lastCheck } = technicaldetails;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{status}</td>
          <td>{type}</td>
          <td>{groupCapacity} - {totalCapacity}</td>
          <td>{fuel}</td>
          <td>{lastCheck}</td>
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
            value='Change status'
          />
        </form>
	
      </div>
    );
  }
}
