/**
 * Created by Hrvoje on 29.3.2016..
 */
import {Injectable, EventEmitter} from "angular2/core";
import {User} from "../models/user.component";
import {Router} from "angular2/router";
declare var Firebase: any;

@Injectable()
export class AuthService{
    private _userLoggedOut = new EventEmitter<any>();
    private _userLoggedIn = new EventEmitter<any>();
    private _user: User;

    constructor(private _router: Router){
        this._router = _router;
    }
    
    singupUser(user: User){
        const firebaseRef = new Firebase("https://sneakers-shop.firebaseio.com");
        firebaseRef.createUser({
            email: user.email,
            password: user.pass
        }, function (error, userData) {
            if(error){
                console.log(error);
            }else{
                console.log('Created user: ' + userData.uid);
            }
        });
    }

    singinUser(user: User){
        const firebaseRef = new Firebase("https://sneakers-shop.firebaseio.com");
        firebaseRef.authWithPassword({
            email: user.email,
            password: user.pass
        }, function (error, authData) {
            if(error){
                console.log(error);
            }else{
                localStorage.setItem('token', authData.token);
                this._userLoggedIn.emit(null);
            }
        });


        
    }

    logout(){
        localStorage.removeItem('token');
        this._userLoggedOut.emit(null);
    }

    isAuthenticated(): boolean{
        return localStorage.getItem('token') !== null;
    }

    getLoggedOutUser(): EventEmitter{
        return this._userLoggedOut;
    }

    getLoggedInUser(): EventEmitter{
        return this._userLoggedIn;
    }
}