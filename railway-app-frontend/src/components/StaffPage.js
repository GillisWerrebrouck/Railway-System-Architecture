import React, { Component } from 'react';
import endpoints from '../api/endpoints';

export default class StaffPage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      staff: [],
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
          <td>{id}</td>
          <td>{lastName}</td>
          <td>{firstName}</td>
          <td>{staffMemberType}</td>
          <td>{formattedBirthdate}</td>
        </tr>
      );
    });
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
      </div>
    );
  }
}
