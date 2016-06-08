/**
 * Created by Hrvoje on 25.05.2016..
 */

import {Injectable, EventEmitter} from "@angular/core";
import {Router} from '@angular/router-deprecated';
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import 'rxjs/add/operator/map';

@Injectable()
export class UserService {

  public userHasLoggedIn: EventEmitter<any> = new EventEmitter();
  public userHasChangedData: EventEmitter<any> = new EventEmitter();

  private address: string = 'http://localhost:5000/api/users';

  constructor(private _http: Http, private _router: Router){

  }

  isUserLoggedIn(): boolean{
    var isLogged: boolean = false;

    if(localStorage.getItem("token")!=null){
      isLogged = true;
    }
    return isLogged;
  }

  loginUser(token, name, photo, email, userId): void{
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId);
    localStorage.setItem('usrPhoto', photo);
    localStorage.setItem('usrName', name);
    localStorage.setItem('usrEmail', email);
    this.userHasLoggedIn.emit('Logged');
  }

  updateUserData(token, name, photo, email, userId): void{
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId);
    localStorage.setItem('usrPhoto', photo);
    localStorage.setItem('usrName', name);
    localStorage.setItem('usrEmail', email);
    this.userHasChangedData.emit('Changed');
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

  logoutUser(): void{
    localStorage.removeItem('token');
    localStorage.removeItem('usrPhoto');
    localStorage.removeItem('usrName');
    localStorage.removeItem('usrEmail');
    this._router.navigate(['Login']);
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


  private extractData(res: Response) {
    let body = res.json();
    return body.data || { };
  }

}
