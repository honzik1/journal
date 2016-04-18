/**
 * Service for notifications
 * @module NotifyService
 */
export default class NotifyService {

    constructor(msg) {
        this.message = (msg==null) ? "Loading data ..." : msg;
        this.callCounter = 1;
        this.failedNotification = false;
    }
    
    close() {
        this.callCounter--;
        
        if(this.failedNotification) return;
        
        if(this.callCounter === 0) {
            console.log(this.message + " SUCCESSFULL");
        }
    }
    
    failed() {
        this.callCounter--;
        this.failedNotification = true;
        console.log(this.message + " FAILED");
    }
    
    addCall() {
        this.callCounter++;
    }

    getCallCounter() {
        return this.callCounter;
    }
    
}