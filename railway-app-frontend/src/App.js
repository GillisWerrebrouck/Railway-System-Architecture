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
            </ul>
        </div>
        <div className="App-intro">
          <Switch>
            <Route exact path="/"  component={NetworkPage} />
            <Route path="/timetable" component={TimetablePage} />
            <Route path="/staff" component={StaffPage} />
            <Route path="/train" component={TrainPage} />
            <Redirect to="/" />
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
