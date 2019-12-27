import React, { Component } from 'react';
import './App.css';

import {
  Route,
  Link,
  Switch,
  Redirect
} from 'react-router-dom';

import NetworkPage from './components/NetworkPage';
import TimetablePage from './components/TimetablePage'
import StaffPage from './components/StaffPage';
import TrainPage from './components/TrainPage';
import TicketPage from './components/TicketPage';
import DelayPage from './components/DelayPage';
import DamagePage from './components/DamagePage';
import StationPage from './components/StationPage';
import TrainDetail from './components/TrainDetail';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Railway app</h1>
        </header>
        <div className="menu">
            <ul>
              <li> <Link to="/">Network</Link> </li>
              <li> <Link to="/timetable">Timetable</Link> </li>
              <li> <Link to="/staff">Staff</Link> </li>
              <li> <Link to="/train">Train</Link> </li>
              <li> <Link to="/ticket">Ticket</Link> </li>
	      <li> <Link to="/delay">Delay</Link> </li>
	      <li> <Link to="/damage">Damage</Link> </li>
	      <li> <Link to="/station">Station</Link> </li>
            </ul>
        </div>
        <div className="App-intro">
          <Switch>
            <Route exact path="/"  component={NetworkPage} />
            <Route path="/timetable" component={TimetablePage} />
            <Route path="/staff" component={StaffPage} />
            <Route path="/train" component={TrainPage} />
            <Route path="/ticket" component={TicketPage} />
            <Route path="/delay" component={DelayPage} />
            <Route path="/damage" component={DamagePage} />
            <Route path="/station" component={StationPage} />
	    <Route path="/trainDetail/:id" component={TrainDetail} />
            <Redirect to="/" />
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
