/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, NavController, IonicApp} from 'ionic-angular';
import {UsersService} from "../../services/user-service/user.service";
import {HomePage} from "../../pages/home.page/home";

@Page({
    templateUrl: 'build/pages/login.page/login.html',
    providers: []
})
export class LoginPage {

    private _isLoading: boolean = false;

    private _passNotOK: boolean = false;
    private _emailNotOK: boolean = false;

    constructor(private nav: NavController, private app: IonicApp, private _userService: UsersService){

    }

    loginUser(email: string, password: string): void {
    this._isLoading = true;

    console.log(email + " " + password);
    var loginSuccessfull: boolean = true;

    this._userService.getUserByEmail(email)
      .subscribe(usr => {
        console.log(usr);

        if(usr.Id > 0){
          this._emailNotOK = false;
          if(password === usr.Password){
            this._passNotOK = false;
          }
          else {
            this._passNotOK = true;
            loginSuccessfull = false;
          }
        }
        else {
          this._emailNotOK = true;
          loginSuccessfull = false;
        }

        if(loginSuccessfull){
          var token = usr.Token;
          var name = usr.Name;
          var emailUsr = usr.Email;
          var photo = usr.UserPhoto;
          var usrId = usr.Id;
          let success = this._userService.loginUser(token, name, photo, emailUsr, usrId);
          if(success){
              let nav = this.app.getComponent('nav');
              nav.setRoot(HomePage);
          }
        }
        this._isLoading = false;
      });
  }


}