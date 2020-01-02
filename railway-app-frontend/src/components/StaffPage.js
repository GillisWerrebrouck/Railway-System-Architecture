import React, { Component } from 'react';
import endpoints from '../api/endpoints';
import { Link } from 'react-router-dom';

export default class StaffPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      staff: [],
      newStaff: {
        firstName: null,
        lastName: null,
        birthdate: null,
        staffMemberType: "CONDUCTOR",
      },
      createStaffErrorResponse: "",
    };
  }

  componentDidMount() {
    endpoints.getStaff()
      .then((result) => {
        this.setState({ staff: result.data, isLoading: false });
      });
  }

  renderStaff() {
    return this.state.staff.map((staffMember, index) => {
      const { id, lastName, firstName, staffMemberType, birthdate } = staffMember;
      
      const date = new Date(birthdate);
      const day = date.getDate();
      const month = date.getMonth() + 1;
      const year = date.getFullYear();
      let formattedBirthdate = (day>=10 ? day : '0' + day) + "-" + 
             (month>=10 ? month : '0' + month) + "-" + 
             year;
      
      return (
        <tr key={id}>
          <td><Link to={`/staff/${id}`}>{id}</Link></td>
          <td>{lastName}</td>
          <td>{firstName}</td>
          <td>{staffMemberType}</td>
          <td>{formattedBirthdate}</td>
        </tr>
      );
    });
  }

  createStaffFormChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;

    let newStaff = {...this.state.newStaff};
    newStaff[name] = value;
    this.setState({ newStaff });
  }

  createStaffItem = (event) => {
    event.preventDefault();

    if(this.state.newStaff.firstName === null || this.state.newStaff.lastName === null || this.state.newStaff.birthdate === null) {
      this.setState({ createStaffErrorResponse: 'All fields need to be filled out' });
      return;
    }

    endpoints.postNewStaff(this.state.newStaff)
      .then(() => { window.location.reload(); })
      .catch(() => { this.setState({ createStaffErrorResponse: 'All fields need to be filled out' }); });
  }

  render() {
    return (
      <div>
        <h2>Staff</h2>

        {!this.state.isLoading ? (
        <table id='staff'>
          <tbody>
            <tr>
              <th>ID</th>
              <th>Last Name</th>
              <th>First Name</th>
              <th>Type</th>
              <th>Birthdate</th>
            </tr>
            { this.renderStaff() }
          </tbody>
        </table>
        ) : (
          <p>Loading...</p>
        )}
        <h3>Add a staff member</h3>
        <form onSubmit={this.createStaffItem}>
          <label>Firstname: </label>
          <input
            type='string'
            name='firstName'
            onChange={this.createStaffFormChangeHandler}
          />
          <br />

        <label>Lastname: </label>
          <input
            type='string'
            name='lastName'
            onChange={this.createStaffFormChangeHandler}
          />
          <br />

          <label>Birthdate: </label>
          <input
            type='datetime'
            name='birthdate'
            onChange={this.createStaffFormChangeHandler}
          />
          <br />

          <label>Staff type: </label>
          <select name='staffMemberType' onChange={this.createStaffFormChangeHandler}>
            <option value="CONDUCTOR">conductor</option>
            <option value="TRAIN_OPERATOR">train operator</option>
            <option value="MECHANIC">mechanic</option>
          </select>
          <br />

          <input
            type='submit'
            value='CREATE'
          />
        </form>

        <p>{this.state.createStaffErrorResponse}</p>
      </div>
    );
  }
}
