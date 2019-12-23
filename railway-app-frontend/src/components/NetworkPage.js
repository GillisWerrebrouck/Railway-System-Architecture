import React, { Component } from 'react';
import axios from 'axios';

export default class NetworkPage extends Component {
  constructor(props) {
    super(props);

    this.getStations();
  }

  getStations() {
    axios.get('http://localhost:8080/station')
      .then(res => console.log(res.data));
  }

  render() {
    return (
      <div>
        <h2>Network</h2>
      </div>
    )
  }
}
