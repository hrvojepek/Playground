/**
 * Created by Hrvoje on 19.5.2016..
 */


import {Injectable, EventEmitter} from "angular2/core";
import {Http, Headers, RequestOptions, Response} from "angular2/http";
import 'rxjs/add/operator/map';
import {StartPage} from "../../pages/start.page/start";
import {HomePage} from "../../pages/home.page/home";


@Injectable()
export class UsersService {

    private address: string = 'http://localhost:5000/api/users';
    public userHasLoggedIn: EventEmitter<any> = new EventEmitter();

    constructor(private _http: Http){

    }

    isUserLoggedIn(): boolean{
        var isLogged: boolean = false;

        if(localStorage.getItem('token')!=null && localStorage.getItem('usrEmail')!=null){
          isLogged = true;
        }
        return isLogged;
    }

  loginUser(token, name, photo, email, userId): boolean{
    localStorage.setItem('token', token);
    localStorage.setItem('usrId', userId);
    localStorage.setItem('usrPhoto', photo);
    localStorage.setItem('usrName', name);
    localStorage.setItem('usrEmail', email);
    return true;
  }

  updateUserData(token, name, photo, email, userId): void{
    localStorage.setItem('token', token);
    localStorage.setItem('usrId', userId);
    localStorage.setItem('usrPhoto', photo);
    localStorage.setItem('usrName', name);
    localStorage.setItem('usrEmail', email);
    //this.userHasChangedData.emit('Changed');
  }

  getUsers(): any{
    return this._http.get(this.address)
      .map(res => res.json());
  }

  getUserByEmail(email: string): any{
    return this._http.get(this.address + '/email/' + email)
      .map(res => res.json());
  }

  getUserByToken(token: string): any{
    return this._http.get(this.address + '/token/' + token)
      .map(res => res.json());
  }

  getUserByID(id: string): any{
    return this._http.get(this.address + '/' + id)
      .map(res => res.json());
  }

  logoutUser(): boolean{
    localStorage.removeItem('token');
    localStorage.removeItem('usrPhoto');
    localStorage.removeItem('usrName');
    localStorage.removeItem('usrEmail');
    localStorage.removeItem('usrId');
    return true;
  }

  registerUser(user: any): any{
    console.log(user);
    let body = JSON.stringify(user);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(res=>res.json());
  }

  updateUser(user: any): any{
    console.log(user);
    let body = JSON.stringify(user);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.put(this.address + '/' + user.Id, body, options)
      .map(res=>res.json());
  }

  deleteUser(userId: number, userEmail: string): any{
    return this._http.delete(this.address + '/' + userId + '/' + userEmail)
      .map(res=>res.json());
  }


  private extractData(res: Response) {
    let body = res.json();
    return body.data || { };
  }
}

