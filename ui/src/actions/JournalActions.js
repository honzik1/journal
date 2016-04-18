import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes } from '../constants/AppConstants';

export default class JournalActions {
	static detail(id) {
        AppDispatcher.dispatch({
            type: ActionTypes.LOAD_JOURNAL_DETAIL,
            id: id
        });
	}
}