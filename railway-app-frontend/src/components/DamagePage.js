import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class DamagePage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      damage: {
	location: null,
        message: null,
      },
      createErrorResponse: "",
    };
  }

  createDamageFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let damage = {...this.state.damage};
    damage[name] = value;
    this.setState({ damage });
  }

  createDamage = (event) => {
    event.preventDefault();
    endpoints.postDamage(this.state.damage)
      .then(result => { 
        if(typeof result.data === "string") {
          this.setState({ createErrorResponse: result.data });
        } else {
          window.location.reload();
        }
      });
  }

  render() {
    return (
      <div>
        <h3>Notify infrastructure damage</h3>
        <form onSubmit={this.createDamage}>
          <label>location: </label>
          <input
            type='text'
            name='location'
            onChange={this.createDamageFormChangeHandler}
          />
          <br />
          <label>message: </label>
          <input
            type='text'
            name='message'
            onChange={this.createDamageFormChangeHandler}
          />
          <br />
          <input
            type='submit'
            value='Notify'
          />
        </form>

        <p>{this.state.createErrorResponse}</p>
      </div>
    );
  }
}
