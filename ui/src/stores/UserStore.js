import FluxStore from './FluxStore';
import AppDispatcher from '../dispatcher/AppDispatcher';
import {ActionTypes} from '../constants/AppConstants';
import RestService from '../services/RestService';
import RouterContainer from '../services/RouterContainer';
import GlobalStore from '../stores/GlobalStore';
import jwt_decode from 'jwt-decode';

let _state = {
    messages:[]
};
const BASE_LIST_URL = '/user';

class UserStore extends FluxStore {


    constructor() {
        super();
        this.dispatchToken = AppDispatcher.register(this._register.bind(this));
        console.log('UserStore created');
    }

    getData(key) {
        return _state[key];
    }

    addChangeListener(callback) {
        super.addChangeListener(callback);
        if (!_state.data)
            this._reloadList();
    }

    _reloadList() {
        let thisRef = this;
        let pagination = {
            start: 0,
            max: 10
        };
        
        _state.data = undefined;

        RestService.httpGet(BASE_LIST_URL, null, function(err, res) {
            
            if(res.ok) {
                if(!pagination) {
                    _state.data = { 
                        lastPage: 0,
                        total: res.body.records.length,
                        data: res.body.records
                    }
                } else {
                    _state.total = res.body.total;
                    _state.data = res.body.records;
                }
                
            } else {
                _state.messages.push({
                    type: "error",
                    code: res.code,
                    text: res.message,
                    title: "Loading Error"
                });
            }
            thisRef.emitChange();
        }, null, null, pagination);
    }

    _loadDetail(id) {
        let thisRef = this;
        _state.detail = undefined;

        RestService.httpGet(BASE_LIST_URL+'/'+id, null, function(err, res) {
            
            if(res.ok) {
                _state.detail = res.body;                
            } else {
                _state.messages.push({
                    type: "error",
                    code: res.code,
                    text: res.message,
                    title: "Loading Error"
                });
            }
            thisRef.emitChange();
        }, null, null, null);
    }

    _login(email, password) {
        let thisRef = this;
        _state.loggedUserId = undefined;

        RestService.httpPost(BASE_LIST_URL+'/login', {email: email, password: password}, null, function(err, res) {
            
            if(res.ok) {
                _state.token = res.body.payload;
                _state.userId = jwt_decode(_state.token).prn;
                console.log('Logged user: ' + _state.userId);
                localStorage.setItem('jwt', _state.token);
                RouterContainer.get().transitionTo('/')
            } else {
                _state.messages.push({
                    type: "error",
                    code: res.code,
                    text: res.message,
                    title: "Login Failed"
                });
            }
            thisRef.emitChange();
        }, true);
    }

    _logout() {
        _state.userId = undefined;
        localStorage.removeItem('jwt');
        RouterContainer.get().transitionTo('/')        
    }

    _register(action) {
        console.log(action);
        switch(action.type) {
            case ActionTypes.USER_UPDATE:
                this.emitChange();
            break;
            case ActionTypes.LOAD_USER_DETAIL:
                this._loadDetail(action.id);
                this.emitChange();
            break;
            case ActionTypes.LOGIN:
                this._login(action.email, action.password);
                this.emitChange();
            break;
            case ActionTypes.LOGOUT:
                this._logout();
                this.emitChange();
            break;
    
            default:
            return;
        }        
    }
}

let UserStoreInstance = new UserStore();

export default UserStoreInstance;