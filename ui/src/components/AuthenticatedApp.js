import React from 'react';
import UserStore from '../stores/UserStore'
import UserActions from '../actions/UserActions'
import { Route, RouteHandler, Link } from 'react-router';
// import AuthService from '../services/AuthService'

export default class AuthenticatedApp extends React.Component {
  constructor(props) {
    super(props)
    this.state = this._getLoginState();
  }

  _getLoginState() {
    return {
      userLoggedIn: UserStore.getData('userId')
    };
  }

  componentDidMount() {
    UserStore.addChangeListener(this._onChange.bind(this));
  }

  _onChange() {
    this.setState(this._getLoginState());
  }

  componentWillUnmount() {
    UserStore.removeChangeListener(this._onChange.bind(this));
  }

  render() {
    return (
      <div className="container well">
        <nav className="navbar navbar-default">
          <div className="navbar-header">
            <a className="navbar-brand" href="/">Journal Database</a>
          </div>
          {this._getHeaderItems()}
        </nav>
        <RouteHandler/>
      </div>
    );
  }

  logout(e) {
    e.preventDefault();
    UserActions.logout();
  }

  _getHeaderItems() {
    if (!this.state.userLoggedIn) {
      return (
      <ul className="nav navbar-nav navbar-right">
        <li>
          <Link to="login">Login</Link>
        </li>
        <li>
          <Link to="signup">Signup</Link>
        </li>
      </ul>)
    } else {
      return (
      <ul className="nav navbar-nav navbar-right">
        <li>
          <a href="" onClick={this.logout}>Logout</a>
        </li>
      </ul>
      )
    }
  }
}
