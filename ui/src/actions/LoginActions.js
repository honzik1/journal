import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes } from '../constants/AppConstants';

export default class UserActions {
	static login(email, password) {
        AppDispatcher.dispatch({
            type: ActionTypes.LOGIN,
            email: email,
            password: password
        });
	}
}