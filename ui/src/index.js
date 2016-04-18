import React from 'react';
import ReactDOM from 'react-dom';
import Router, {Route} from 'react-router';
import Journals from './components/Journals';
import Journal from './components/Journal';
import Login from './components/Login';
import Signup from './components/Signup';
import AuthenticatedApp from './components/AuthenticatedApp';
import RouterContainer from './services/RouterContainer';

var routes = (
  <Route handler={AuthenticatedApp}>
    <Route name="journal" path="/journal/:detailId" handler={Journal}/>
    <Route name="journals" path="/" handler={Journals}/>
    <Route name="login" handler={Login}/>
    <Route name="signup" handler={Signup}/>
  </Route>
);

var router = Router.create({routes});
RouterContainer.set(router);

router.run(function (Handler) {
  ReactDOM.render(<Handler />, document.getElementById('root'));
});

