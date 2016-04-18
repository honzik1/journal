/**
 * REST service client.
 * @module RestService
 */
import superagent  from 'superagent';

import GlobalStore from "../stores/GlobalStore";
import {ActionTypes} from '../constants/AppConstants';
import NotifyService from "../services/NotifyService"

const GS = GlobalStore;

function errorCallback(msg) {
    GS.getProperty("notifications")[msg].failed();

    if( GS.getProperty("notifications")[msg].getCallCounter() === 0 ) {
        GS.getProperty("notifications")[msg] = undefined;
    }
}

function successCallback(msg) {
    GS.getProperty("notifications")[msg].close();

    if( GS.getProperty("notifications")[msg].getCallCounter() === 0 ) {
        GS.getProperty("notifications")[msg] = undefined;
    }
}

function httpInit(msg) {
    if(GS.getProperty("notifications")[msg] === undefined) {
        GS.getProperty("notifications")[msg] = new NotifyService(msg);
    }
    else {
        GS.getProperty("notifications")[msg].addCall();
    }
}


export default class RestService {
    
    static _authAgent(agent) {
        if (localStorage.getItem('jwt')) {
            return agent.set('Authorization', 'Bearer '+localStorage.getItem('jwt'));
        } else {
            return agent;    
        }
    }
    static httpGet(url, msg, callback, filter, sortby, pagination) {
        if(msg === null) msg = "Loading data ...";
        httpInit(msg);
        
        var params = Array();
        
        if(filter && filter !== '') params.push('filter=' + filter);
        if(sortby) {
            params.push('key=' + sortby.key);
            params.push('order=' + sortby.order);
        }
        if(pagination) {
            params.push('start=' + pagination.start);
            params.push('max=' + pagination.max);
        }
        
        if(params.length !== 0) url += '?' + params.join('&');
        
                
        RestService._authAgent(superagent.get(GS.getProperty('apiURL') + url))
            // .set('Access-Control-Allow-Origin', 'localhost')
            // .set('Access-Control-Allow-Headers', 'Content-Type')
            .end( function(err, res){
                if(err && !res) {
                    res = {};
                    res.ok = false;
                    res.status = 500;
                    res.statusCode = res.status;
                    res.statusText = "Server not available.";
                }
                
                if(!res.ok) errorCallback(msg);
                else successCallback(msg);
                
                if(callback !== undefined || callback !== null) {
                    callback(err, res);
                }
            }.bind(this));
    }
    
    static httpPost(url, data, msg, callback, formData = false) {
        GS.setProperty("lock", true);
        if(msg === null) msg = "Posting data ...";
        httpInit(msg);
        
        if (data.id !== undefined) {
            data.id = parseInt(data.id);
        }
        
        superagent.post(GS.getProperty('apiURL') + url)
            .type(formData ? 'form' : 'json')
            .send(data)
            .end( function(err, res){
                if(err && !res) {
                    res = {};
                    res.ok = false;
                    res.status = 500;
                    res.statusCode = res.status;
                    res.statusText = "Server not available.";
                }
                
                if(!res.ok) errorCallback(msg);
                else successCallback(msg);
                
                GS.setProperty("lock", false);
                
                if(callback !== undefined || callback !== null) {
                    callback(err, res);
                }
            }.bind(this));
    }
    
    static httpPut(url, data, msg, callback) {
        GS.setProperty("lock", true);
        if(msg === null) msg = "Updating data ...";
        httpInit(msg);
        
        data.id = parseInt(data.id);
        
        superagent.put(GS.getProperty('apiURL') + url)
            .send(data)
            .end( function(err, res){
                if(err && !res) {
                    res = {};
                    res.ok = false;
                    res.status = 500;
                    res.statusCode = res.status;
                    res.statusText = "Server not available.";
                }
                
                if(!res.ok) errorCallback(msg);
                else successCallback(msg);
                
                GS.setProperty("lock", false);
                
                if(callback !== undefined || callback !== null) {
                    callback(err, res);
                }
            }.bind(this));
    }
    
    static httpDelete(url, msg, callback) {
        GS.setProperty("lock", true);
        if(msg === null) msg = "Deleting data ...";
        httpInit(msg);
        
        superagent.del(GS.getProperty('apiURL') + url)
            .end( function(err, res){
                if(err && !res) {
                    res = {};
                    res.ok = false;
                    res.status = 500;
                    res.statusCode = res.status;
                    res.statusText = "Server not available.";
                }
                
                if(!res.ok) errorCallback(msg);
                else successCallback(msg);
                
                GS.setProperty("lock", false);
                
                if(callback !== undefined || callback !== null) {
                    callback(err, res);
                }
            }.bind(this));
    }
    
}