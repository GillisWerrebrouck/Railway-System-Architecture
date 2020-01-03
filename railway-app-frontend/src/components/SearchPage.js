import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class SearchPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      stations: [],
      searchFilter: {
        startStationId: null,
        endStationId: null,
        fromDate: null,
        toDate: null,
      },
      searchResults: [],
      routeDetails: [],
    };
  }

  componentDidMount() {
    this.getStations();
  }

  getStations() {
    endpoints.getStations()
      .then((result) => {
        this.setState({ stations: result.data });
      });
  }

  searchFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let searchFilter = {...this.state.searchFilter};
    searchFilter[name] = value;
    this.setState({ searchFilter });
  }

  search = (event) => {
    event.preventDefault();

    const {startStationId, endStationId ,fromDate, toDate} = {...this.state.searchFilter};

    if(startStationId === null || endStationId === null || fromDate === null || toDate === null) {
        return;
    }

    this.setState({ routeDetails: [] });

    endpoints.search(startStationId, endStationId ,fromDate, toDate)
      .then((result) => { this.setState({ searchResults: result.data }); })
      .catch((error) => {console.error(error); });
  }

  selectRoute = (routeId) => {
      endpoints.getRouteDetails(routeId)
        .then((result) => { this.setState({ routeDetails: result.data }); })
        .catch((error) => {console.error(error); });
  }

  render() {
    return (
      <div>
        <h2>Search</h2>

        <p>
          Searching for timetable items requires a start station, end station and a datetime interval. Mind the direction in which 
          you wish to travel, requesting information about timetable items going from station x to station y will result in different 
          (if available) results than if you would be going from station y to station x. It is only possible to search for single route 
          destinations at this time. This means that two stations that are not connected with a route, but could be reached with multiple 
          routes will not show up due to the fact that the algorithm necessary for this has not been implemented.
        </p>

        <p>
          Only when a search has a result it will show up in a table. Clicking on the ID in that table will display the route details. 
          Routedetails are all stations in chronological order with some details included.
        </p>

        <form onSubmit={this.search}>
          <label>Start station: </label>
          <select name='startStationId' onChange={this.searchFormChangeHandler}>
            <option value="">choose a station</option>
            {this.state.stations.map((station) => {
              return (<option key={station.id} value={station.id}>{station.name}</option>)
            })}
          </select>
          <br />

          <label>End station: </label>
          <select name='endStationId' onChange={this.searchFormChangeHandler}>
            <option value="">choose a station</option>
            {this.state.stations.map((station) => {
              return (<option key={station.id} value={station.id}>{station.name}</option>)
            })}
          </select>
          <br />

          <label>From datetime: </label>
          <input
            type='text'
            name='fromDate'
            onChange={this.searchFormChangeHandler}
          />
          <span>&nbsp;format: yyyy-mm-ddThh:mm:00.000</span>
          <br />

          <label>To datetime: </label>
          <input
            type='text'
            name='toDate'
            onChange={this.searchFormChangeHandler}
          />
          <span>&nbsp;format: yyyy-mm-ddThh:mm:00.000</span>
          <br />

          <input
            type='submit'
            value='SEARCH'
          />
        </form>

        <br />

        {
          this.state.searchResults.length > 0 ?
            <div>
              <table id='searchResults'>
                <tbody>
                  <tr>
                    <th>ID</th>
                    <th>Start datetime</th>
                    <th>End datetime</th>
                    <th>Delay (in minutes)</th>
                    <th>Reason for delay</th>
                    <th>Route ID</th>
                    <th>Route</th>
                  </tr>
                  {
                    this.state.searchResults.map((item) => {
                      return (
                          <tr key={item.id}>
                          <th id="clickable" onClick={() => this.selectRoute(item.id)}>{item.id}</th>
                          <th>{item.startDateTime}</th>
                          <th>{item.endDateTime}</th>
                          <th>{item.delay}</th>
                          <th>{item.reasonForDelay ? item.reasonForDelay : '---'}</th>
                          <th>{item.routeId}</th>
                          <th>{item.route}</th>
                        </tr> 
                      );
                    })
                  }
                </tbody>
              </table> 

              <br />

              {
                this.state.routeDetails.length > 0 ?
                  <table id='routeDetails'>
                    <tbody>
                      <tr>
                        <th>Station</th>
                        <th>Platform number</th>
                        <th>Arrival datetime</th>
                        <th>Departure datetime</th>
                        <th>Delay</th>
                      </tr>
                      {
                        this.state.routeDetails.map((item) => {
                          return (
                            <tr key={item.stationId}>
                              <th>{item.name}</th>
                              <th>{item.platformNumber}</th>
                              <th>{item.arrivalDateTime}</th>
                              <th>{item.departureDateTime}</th>
                              <th>{item.delay}</th>
                            </tr> 
                          );
                        })
                      }
                    </tbody>
                  </table> 
                : null
              }
            </div>
          : null
        }
      </div>
    );
  }
}
