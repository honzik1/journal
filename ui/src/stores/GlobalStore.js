import React from 'react';
import FluxStore from './FluxStore';
import AppDispatcher from '../dispatcher/AppDispatcher';
import {ActionTypes} from '../constants/AppConstants';

let _props = {};

/** DEFAULT VALUES **/
_props.serverURL = '//' + window.location.host + '/';
_props.apiURL = '//' + window.location.hostname + ':8080/journal/api/v1';
_props.notifications = {};
_props.lock = false;

_props.getRandomInt = function(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
};

_props.topNavbar = {
    title: "Home",
    index: "Index...",
    items: {
        right: [
            { 
                title: (
                    <span>
                        <span className="glyphicon glyphicon-user"></span> Sign Up
                    </span>
                ),
                url: "#/" 
            },
            { 
                title: (
                    <span>
                        <span className="glyphicon glyphicon-log-in"></span> Login
                    </span>
                ),
                url: "#/" 
            }
        ]
    }
};
/** DEFAULT VALUES END**/

function get(key) {
    return _props[key];
}

function set(key, prop) {
    _props[key] = prop;
}


class GlobalStore extends FluxStore {

  constructor() {
    super();
  }

    getProperty(key) {
        return get(key);
    }
    
    setProperty(key, prop) {
        set(key, prop);
    }

}

let GlobalStoreInstance = new GlobalStore();

GlobalStoreInstance.dispatchToken = AppDispatcher.register(action => {

  switch(action.type) {
    case ActionTypes.GLOBAL_UPDATE:
      console.log(action);
      set(action.key, action.prop);
      GlobalStoreInstance.emitChange();
    break;
    // case ActionTypes.APP_RESET:
    //   reset();
    //   break;

    // case ActionTypes.POUCH_ERROR:
    //   appState.message = 'Local database error: ' + action.error.message;
    //   break;

    default:
    return;
  }


});

export default GlobalStoreInstance;