/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Component} from '@angular/core';
import {Router, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {SpinnerComponent} from "../../help-components/spinner-component/spinner.component";
import {UserService} from "../../services/user-service/UserService";

@Component({
  selector: 'login',
  providers: [],
  pipes: [],
  directives: [ROUTER_DIRECTIVES, SpinnerComponent],
  templateUrl: 'app/pages/login/login.html',
})

export class LoginPage {

  private _passNotOK: boolean = false;
  private _emailNotOK: boolean = false;
  private _isLoading: boolean = false;

  private _forgotPassShowing: boolean = false;

  constructor(private _userService: UserService, private _router: Router){
    if(this._userService.isUserLoggedIn()){
        this._router.navigate(['Home']);
    }
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
          console.log("NIJENASAO");
          loginSuccessfull = false;
        }

        if(loginSuccessfull){
          var token = usr.Token;
          var name = usr.Name;
          var emailUsr = usr.Email;
          var photo = usr.UserPhoto;
          var usrId = usr.Id;
          this._userService.loginUser(token, name, photo, emailUsr, usrId);
        }
        this._isLoading = false;
      });
  }

  sendPass(email: string): void {
    console.log(email);
  }

}
