import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class StationPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      stations: [],
      station: {
        name: null,
        address: {
          street: null,
          city: null,
          province: null,
          country: null,	
        },
        platforms: [],
      },
      platform: {
        stationId: null,
        platformNumber: null,
        reservedSlots: null,
      },
      addStationErrorResponse: "",
      addPlatformErrorResponse: "",
    };
  }

  componentDidMount() {
    endpoints.getStations()
      .then((result) => {
        this.setState({ stations: result.data, isLoading: false });
      });
  }

  renderStation() {
    return this.state.stations.map((station, index) => {
      const { id, name, address } = station;
      const { street, city, province, country } = address;
      return (
        <tr key={id}>
          <td>{id}</td>
          <td>{name}</td>
          <td>{street}</td>
          <td>{city}</td>
          <td>{province}</td>
          <td>{country}</td>
        </tr>
      );
    });
  }

  addStationFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let station = {...this.state.station};
    
    if(['street','city','province','country'].includes(name)){
	    station['address'][name] = value;
    } else{
    	station[name] = value;
    }
    this.setState({ station });
  }

  addStation = (event) => {
    event.preventDefault();
    endpoints.postNewStation(this.state.station)
      .then(() => window.location.reload())
      .catch((error) => { this.setState({ addStationErrorResponse: error }); });
  }

  addPlatformFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let platform = {...this.state.platform};

    platform[name] = value;
 
    this.setState({ platform });
  }

  addPlatform = (event) => {
    event.preventDefault();
    endpoints.postNewPlatform(this.state.platform)
      .then(() => window.location.reload())
      .catch((error) => { this.setState({ addPlatformErrorResponse: error }); });
  }

  render() {
    return (
      <div>
        <h2>Station</h2>

        {!this.state.isLoading ? (
        <table id='station'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Street</th>
              <th>City</th>
              <th>Province</th>
              <th>Country</th>
            </tr>
            { this.renderStation() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}

        <h3>Add station</h3>
        <form onSubmit={this.addStation}>
          <label>Name: </label>
          <input
            type='text'
            name='name'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>Street: </label>
          <input
            type='text'
            name='street'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>City: </label>
          <input
            type='text'
            name='city'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>Province: </label>
          <input
            type='text'
            name='province'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>Country: </label>
          <input
            type='text'
            name='country'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <input
            type='submit'
            value='CREATE'
          />
        </form>
        <p>{this.state.addStationErrorResponse}</p>

	      <h3>Add platform to station</h3>
        <form onSubmit={this.addPlatform}>
          <label>Station id: </label>
          <input
            type='text'
            name='stationId'
            onChange={this.addPlatformFormChangeHandler}
          />
          <br />

          <label>Platform number: </label>
          <input
            type='number'
            name='platformNumber'
            onChange={this.addPlatformFormChangeHandler}
          />
          <br />

          <input
            type='submit'
            value='CREATE'
          />
        </form>
        <p>{this.state.addPlatformErrorResponse}</p>
      </div>
    );
  }
}
