import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes } from '../constants/AppConstants';

export default class LoginActions {
	static login(email, password) {
        AppDispatcher.dispatch({
            type: ActionTypes.LOGIN,
            email: email,
            password: password
        });
	}
	static logout() {
        AppDispatcher.dispatch({
            type: ActionTypes.LOGOUT
        });
	}
}