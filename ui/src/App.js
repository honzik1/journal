import React, { Component } from 'react';
import Journals from './components/Journals';

export default class App extends Component {
  render() {
    return (
    	<div className="container ">
	      <Journals />
	    </div>
    );
  }
}