import React from 'react/addons';
import ReactMixin from 'react-mixin';
// import Auth from '../services/AuthService'

export default class Signup extends React.Component {

  constructor() {
    super()
    this.state = {
      email: '',
      password: '',
      firstName: '',
      lastName: ''
    };
  }

  signup(e) {
    e.preventDefault();
    // Auth.signup(this.state.email, this.state.password, this.state.firstName, this.state.lastName)
    //   .catch(function(err) {
    //     alert("There's an error logging in");
    //     console.log("Error logging in", err);
    //   });
    console.log('signup');
  }

  render() {
    return (
      <div className="login jumbotron center-block">
        <h1>Signup</h1>
        <form role="form">
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input type="text" valueLink={this.linkState('email')} className="form-control" id="email" placeholder="email" />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input type="password" valueLink={this.linkState('password')} className="form-control" id="password" ref="password" placeholder="Password" />
        </div>
        <div className="form-group">
          <label htmlFor="firstName">Name</label>
          <input type="text" valueLink={this.linkState('firstName')} className="form-control" id="firstName" ref="firstName" placeholder="Your Name" />
        </div>
        <div className="form-group">
          <label htmlFor="lastName">Surname</label>
          <input type="text" valueLink={this.linkState('lastName')} className="form-control" id="lastName" ref="lastName" placeholder="Your surname" />
        </div>
        <button type="submit" className="btn btn-default" onClick={this.signup.bind(this)}>Submit</button>
      </form>
    </div>
    );
  }
}

ReactMixin(Signup.prototype, React.addons.LinkedStateMixin);
