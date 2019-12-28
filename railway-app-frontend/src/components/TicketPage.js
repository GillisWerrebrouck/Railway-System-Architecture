import React, { Component } from 'react';
import Moment from 'moment';
import endpoints from "../api/endpoints";

export default class TicketPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
        isLoading: true,
        tickets: [],
        timetable: [],
        startStations: [],
        endStations: [],
        newTicket: {
            timetableId: null,
            startDateTime: null,
            startStationId: null,
            endStationId: null,
            ticketType: null,
            amountOfSeats: 0
        },
        buyTicketErrorResponse: "",
    };
  }

  onTicketChange = (event) => {
      const name = event.target.name;
      const value = event.target.value;

      let newTicket = {...this.state.newTicket};
      newTicket[name] = value;
      const timetable = this.findTimetableItemWithId(newTicket.timetableId);

      if(timetable != null){
          endpoints.getStationsOnRoute(timetable.routeId).then((result) => {
              this.setState({startStations: result.data});
          });
          newTicket.startDateTime = timetable.startDateTime;
      }

      if(newTicket.startStationId != null){
          const index = this.state.startStations.findIndex(station => station.stationId === newTicket.startStationId);
          this.setState({endStations: this.state.startStations.slice(index+1)});
      }

      this.setState({ newTicket });
  };

  findTimetableItemWithId(id) {
        return this.state.timetable.find((item) => {
            return item.id === id;
        })
    }

  componentDidMount() {
    endpoints.getTickets()
        .then((result) => {
          this.setState({ tickets: result.data });
          endpoints.getTimetable().then((result) => {
              this.setState({timetable: result.data, isLoading: false});
              endpoints.getStationsOnRoute(this.state.timetable[0].routeId).then((result) => {
                  this.setState({startStations: result.data, endStations: result.data.slice(1)});
                  this.setState({newTicket: {                     // specific object of food object
                          ...this.state.newTicket,
                          timetableId: this.state.timetable[0].id,
                          startDateTime: this.state.timetable[0].startDateTime,
                          startStationId: this.state.startStations[0].stationId,
                          endStationId: this.state.endStations[0].stationId
                      }});
              });
          })
        });
  }

  buyTicket = (event) => {
        event.preventDefault();
        endpoints.postNewTicket(this.state.newTicket)
            .then(result => {
                if(typeof result.data === "string") {
                    this.setState({ createTimetableItemErrorResponse: result.data });
                } else {
                    window.location.reload();
                }
            });
    }

  renderTickets() {
    return this.state.tickets.map((ticket, index) => {
      const { id, startStation, endStation, validOn, price, amountOfSeats, type, validationCode, status } = ticket;
      return (
          <tr key={id}>
            <td>{id}</td>
            <td>{startStation}</td>
            <td>{endStation}</td>
            <td>{validOn}</td>
            <td>{type}</td>
            <td>{amountOfSeats}</td>
            <td>{price}</td>
            <td>{validationCode}</td>
            <td>{status}</td>
          </tr>
      );
    });
  }
  render() {
    return (
      <div>
        <h2>Ticket</h2>
        {!this.state.isLoading ? (
            <table id='trains'>
              <tbody>
              <tr>
                <th>ID</th>
                <th>StartStation</th>
                <th>EndStation</th>
                <th>ValidOn</th>
                <th>Ticket type</th>
                <th>Amount of seats</th>
                <th>Price</th>
                <th>Validation code</th>
                <th>Status</th>
              </tr>
              { this.renderTickets() }
              </tbody>
            </table>
        ) : (
            <p>Loading...</p>
        )}
        <h2>Buy a new ticket</h2>
          <form onSubmit={this.buyTicket}>
              <label>Timetable: </label>
              <select
                  name='timetableId'
                  onChange={this.onTicketChange}>
                  {this.state.timetable.map((t, key) => {
                      return <option key={key} value={t.id}>{t.route} : {Moment(t.startDateTime).format('DD/MM-HH:mm')}</option>;
                  })}
              </select>
              <br />
              <label>Start Station: </label>
              <select
                  name='startStationId'
                  onChange={this.onTicketChange}>
                  {this.state.startStations.map((t, key) => {
                      return <option key={key} value={t.stationId}>{t.name}</option>;
                  })}
              </select>
              <br />
              <label>End Station: </label>
              <select name='endStationId' onChange={this.onTicketChange}>
                  {this.state.endStations.map((t, key) => {
                      return <option key={key} value={t.stationId}>{t.name}</option>;
                  })}
              </select>
              <br />
              <label>Ticket type: </label>
              <input
                  name='ticketType'
                  type='radio'
                  value='single' onChange={this.onTicketChange} />
                  Single
              <input
                  name='ticketType'
                  type='radio'
                  value='group' onChange={this.onTicketChange} />
                  Group
              <br/>
              {this.state.newTicket.type != null &&
                  <div>
                <label>amount of seats</label>
                <input
                  type='number'
                  name='amountOfSeats'
                  min={this.state.newTicket.ticketType === 'group' ? 10 : 1 }
                  defaultValue={this.state.newTicket.ticketType === 'group' ? 10 : 1 }
                  onChange={this.onTicketChange}
                  />
                  </div>
              }
              <input
                  type='submit'
                  value='buy'
              />
          </form>
          <p>{this.state.buyTicketErrorResponse}</p>
      </div>
    );
  }

}


