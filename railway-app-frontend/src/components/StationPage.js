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
        platforms: null,
      },
      addStationErrorResponse: "",
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
    
    if(name === 'street' || name === 'city' || name === 'province' || name === 'country'){
	station['address'][name] = value;
    } else{
    	station[name] = value;
    }
    this.setState({ station });
  }

  addStation = (event) => {
    event.preventDefault();
    endpoints.postNewStation(this.state.station)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ addStationErrorResponse: result.data });
        } else {
          window.location.reload();
        }
      });
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
              <th>name</th>
              <th>street</th>
              <th>city</th>
              <th>province</th>
              <th>country</th>
            </tr>
            { this.renderStation() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}

        <h3>add station</h3>
        <form onSubmit={this.addStation}>
          <label>name: </label>
          <input
            type='text'
            name='name'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>street: </label>
          <input
            type='text'
            name='street'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>city: </label>
          <input
            type='text'
            name='city'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>province: </label>
          <input
            type='text'
            name='province'
            onChange={this.addStationFormChangeHandler}
          />
          <br />

          <label>country: </label>
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
      </div>
    );
  }
}
