import AppDispatcher from '../dispatcher/AppDispatcher';
import { ActionTypes } from '../constants/AppConstants';


export function addItem() {
  AppDispatcher.handleViewAction({
    actionType: ActionTypes.NEW_FOLDER,
  });
}

export function saveItem(text) {
  AppDispatcher.handleViewAction({
    actionType: ActionTypes.SAVE_FOLDER,
    text: text,
  });
}

export function removeItem(index) {
  AppDispatcher.handleViewAction({
    actionType: ActionTypes.REMOVE_FOLDER,
    index: index,
  });
}
