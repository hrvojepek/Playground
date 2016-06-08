/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Injectable} from "angular2/core";
import {Http} from "angular2/http";
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

    private _address: string = 'http://localhost:5000/api/users';

    constructor(private _http: Http){

    }

    isUserLoggedIn(): boolean{
        var isLogged: boolean = false;

        if(localStorage.getItem('userToken')!=null){
            isLogged = true;
        }
        return isLogged;
    }

    getUsers(): any{
        return this._http.get(this._address)
            .map(res => res.json());
    }

    saveUserTokenToLocalStorage(id: string){
        localStorage.setItem('userToken', id);
    }

    getUserTokenToLocalStorage(): string {
        return localStorage.getItem('userToken');
    }
}

