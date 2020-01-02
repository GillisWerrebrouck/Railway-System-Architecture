import React, { Component } from 'react';
import Moment from 'moment';
import DayPickerInput from 'react-day-picker/DayPickerInput';
import 'react-day-picker/lib/style.css';
import endpoints from "../api/endpoints";

export default class TicketPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
        isLoading: true,
        tickets: [],
        timetable: [],
        routes: [],
        startStations: [],
        endStations: [],
        newTicket: {
            routeId: null,
            timetableId: null,
            startDateTime: null,
            startStationId: null,
            endStationId: null,
            ticketType: null,
            amountOfSeats: 1
        },
        buyTicketErrorResponse: "",
        validationCode: "",
        validateTicketErrorResponse: "",
        validatedTicket: null
    };
  }

  onRouteChange= (event) => {
      const routeId = event.target.value;
      let newTicket = {...this.state.newTicket};
      endpoints.getStationsOnRoute(routeId).then((result) => {
          this.setState({newTicket, startStations: result.data, endStations: result.data.slice(1)}, () => {
              newTicket.routeId = routeId;
              newTicket.startStationId = this.state.startStations[0].stationId;
              newTicket.endStationId = this.state.endStations[0].stationId;
              this.setState({newTicket});
          })
      });
  };

  onTimetableChange = (event) => {
      let newTicket = {...this.state.newTicket};
      const timetableId = parseInt(event.target.value);
      const timetable = this.findTimetableItemWithId(timetableId);
      if(timetable != null){
          endpoints.getStationsOnRoute(timetable.routeId).then((result) => {
              this.setState({startStations: result.data, endStations: result.data.slice(1)}, () => {
                  newTicket.timetableId = timetableId;
                  newTicket.startStationId = this.state.startStations[0].stationId;
                  newTicket.endStationId = this.state.endStations[0].stationId;
                  newTicket.startDateTime = timetable.startDateTime;
                  this.setState({newTicket});
                  console.log(newTicket);
              });
          });
      }
  };

  onStartStationChange = (event) => {
      let newTicket = {...this.state.newTicket};
      newTicket.startStationId = event.target.value;
      const index = this.state.startStations.findIndex(station => station.stationId === newTicket.startStationId);
      this.setState({endStations: this.state.startStations.slice(index+1)});
      if(index+1 < this.state.startStations.length)
          newTicket.endStationId = this.state.startStations[index+1].stationId;
      else
          newTicket.endStationId = null;
      this.setState({ newTicket });
  };

  onTypeChange = (event) => {
      const type = event.target.value;
      let newTicket = {...this.state.newTicket};
      for (let key in newTicket ) {
          newTicket[key] = null;
      }
      newTicket.ticketType = type;
      type === "GROUP" ? newTicket.amountOfSeats = 10 : newTicket.amountOfSeats = 1;
      this.setState({ newTicket });
  }

  onTicketChange = (event) => {
      const name = event.target.name;
      const value = event.target.value;

      let newTicket = {...this.state.newTicket};
      newTicket[name] = value;
      this.setState({ newTicket });
  };

  onDayChange = (day) => {
      let newTicket = {...this.state.newTicket};
      newTicket.startDateTime = day;
      this.setState({ newTicket });
      console.log(newTicket);
  };

  onValidationCodeChange = (event) => {
      const validationCode = event.target.value;
      this.setState({validationCode: validationCode});
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
              this.setState({timetable: result.data});

              endpoints.getRoutes().then((result)=> {
                  this.setState({routes: result.data, isLoading: false})
                  console.log(this.state.routes);
              })
          });
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
  };

  validateTicket = (event) => {
      event.preventDefault();
      endpoints.validateTicket(this.state.validationCode)
          .then(result => {
              if(typeof result.data === "string") {
                  this.setState({ validateTicketErrorResponse: result.data });
              } else {
                  this.setState({validatedTicket: result.data});
              }
          });
  };

  renderSingleTicketSale(){
      const today = new Date();
      return (
          <div>
              <label>Route: </label>
              <select
                  name='routeId'
                  onChange={this.onRouteChange}
                  defaultValue="select route"
              >
                  <option key='default' disabled={true} >select route</option>
                  {this.state.routes.map((r, key) => {
                      return <option type="number" key={key} value={r.id}>{r.name}</option>;
                  })}
              </select>
              {this.state.newTicket.routeId != null && (
                  <div>
                      <label>Start Station: </label>
                      <select
                          name='startStationId'
                          onChange={this.onStartStationChange}>
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
                      <label>Date: </label>
                      <DayPickerInput
                          onDayChange={day => this.onDayChange(day)}
                          dayPickerProps={{
                              modifiers: {
                                  disabled: {
                                      before: today
                                  }
                              }
                          }}
                      />
                  </div>
              )}
          </div>
      )
  };

  renderGroupTicketSale() {
      return (
          <div>
              <label>Timetable: </label>
              <select
                  name='timetableId'
                  onChange={this.onTimetableChange}
                  defaultValue="select timetable">
                  <option key='default' disabled={true}>select timetable</option>
                  {this.state.timetable.map((t, key) => {
                      return <option key={key} value={t.id}>{t.route} : {Moment(t.startDateTime).format('DD/MM-HH:mm')}</option>;
                  })}
              </select>
              <br />
              <label>Start Station: </label>
              <select
                  name='startStationId'
                  onChange={this.onStartStationChange}>
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
          </div>
      )
   };
  renderTickets() {
    return this.state.tickets.map((ticket, index) => {
      const { id, startStation, endStation, validOn, price, amountOfSeats, type, validationCode } = ticket;
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
              </tr>
              { this.renderTickets() }
              </tbody>
            </table>
        ) : (
            <p>Loading...</p>
        )}
        <h2>Buy a new ticket</h2>
          <form onSubmit={this.buyTicket}>
              <label>Ticket type: </label>
              <input
                  name='ticketType'
                  type='radio'
                  value='SINGLE' onChange={this.onTypeChange} />
              Single
              <input
                  name='ticketType'
                  type='radio'
                  value='GROUP' onChange={this.onTypeChange} />
              Group
              <br/>
              {this.state.newTicket.ticketType != null &&
                  <div>
                      <label>amount of seats</label>
                      <input
                          type='number'
                          name='amountOfSeats'
                          min={this.state.newTicket.ticketType === 'GROUP' ? 10 : 1 }
                          defaultValue={this.state.newTicket.ticketType === 'GROUP' ? 10 : 1 }
                          onChange={this.onTicketChange}
                      />
                      {this.state.newTicket.ticketType === "GROUP" ? (
                          this.renderGroupTicketSale()
                      ) : (
                          this.renderSingleTicketSale()
                      )}
                      <input
                          type='submit'
                          value='buy'
                      />
                  </div>

              }
          </form>
          <p>{this.state.buyTicketErrorResponse}</p>
          <h2>Validate a ticket</h2>
          <form onSubmit={this.validateTicket}>
            <label>Validation Code</label>
              <input name="validationCode" onChange={this.onValidationCodeChange} />
              <input
                  type='submit'
                  value='validate'
              />
          </form>
          {this.state.validatedTicket != null && (
              <div>
              <h4 style={{color: this.state.validatedTicket.valid ? 'green' : 'red'}}>Ticket
                  {this.state.validatedTicket.valid ? ( <span> valid</span>) : (<span> unValid</span>)}
              </h4>
                  startStation: {this.state.validatedTicket.startStation}<br/>
                  endStation: {this.state.validatedTicket.endStation}<br/>
                  amountOfSeats: {this.state.validatedTicket.amountOfSeats}<br/>
                  {this.state.validatedTicket.amountOfSeats > 1 ? (
                      <div>
                          validOn: {Moment(this.state.validatedTicket.validOn).format('DD/MM/YYYY-HH:mm')}
                      </div>
                  ) : (
                      <div>
                          validOn: {Moment(this.state.validatedTicket.validOn).format('DD/MM/YYYY')}
                      </div>
                      )}
                  {this.state.validatedTicket.used && !this.state.validatedTicket.valid && (
                      <span style={{color: 'red'}}>already used.</span>
                  )}
              </div>
          )}
          <p>{this.state.validateTicketErrorResponse}</p>
      </div>
    );
  }

}



