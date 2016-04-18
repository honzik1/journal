import FluxStore from './FluxStore';
import AppDispatcher from '../dispatcher/AppDispatcher';
import {ActionTypes} from '../constants/AppConstants';
import RestService from '../services/RestService';

let _state = {
    messages: []
};
const BASE_LIST_URL = '/journal';

class JournalStore extends FluxStore {


    constructor() {
        super();
        this.dispatchToken = AppDispatcher.register(this._register.bind(this));
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
                console.log(_state.detail);
            } else {
                _state.messages.push({
                    type: "error",
                    code: res.code,
                    text: res.message,
                    title: "Loading Error"
                });
            }
            console.log('EMIT');
            thisRef.emitChange();
        }, null, null, null);
    }

    _register(action) {
        console.log(action);
        switch(action.type) {
            case ActionTypes.JOURNAL_UPDATE:
                this.emitChange();
            break;
            case ActionTypes.LOAD_JOURNAL_DETAIL:
                this._loadDetail(action.id);
                this.emitChange();
            break;
    
            default:
            return;
        }        
    }
}

let JournalStoreInstance = new JournalStore();

export default JournalStoreInstance;