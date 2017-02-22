/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, IonicApp, NavController} from 'ionic-angular';
import {UsersService} from "../../services/user-service/user.service";
import {HomePage} from "../../pages/home.page/home";

@Page({
    templateUrl: 'build/pages/registration.page/registration.html',
    providers: []
})
export class RegistrationPage {

    private _isLoading: boolean = false;

    private emailExists: boolean = false;
    private passLengthNotOk: boolean = false;
    private passwordsNotMatch: boolean = false;
    private allowRegister: boolean = false;

    private email: string = '';
    private pass: string = '';
    private passConf: string = '';
    private name: string = '';
    private surname: string = '';

    constructor(private nav: NavController, private _userService: UsersService, private app: IonicApp){
    }


    registerUser(name: string, email: string, pass: string): void {
        console.log(name + email + pass);

    }

    checkIfEmailExists(email: string){

    this._userService.getUserByEmail(email)
      .subscribe( data => {
        console.log(data);
        if(data.Id==0){
          this.emailExists = false;
          this.email = email;
        }
        else{
          this.emailExists = true;
        }
        this.checkAll();
      });
  }

  setLastName(lastName: string): void{
    this.surname = lastName;
    this.checkAll();
  }

  setName(name: string): void{
    this.name = name;
    this.checkAll();
  }

  checkPasswords(pass: string, passConf: string): void{
    if(pass.length<4){
      this.passLengthNotOk = true;
    }
    else{
      this.passLengthNotOk = false;
      this.pass = pass;
    }

    if(passConf === pass){
      this.passwordsNotMatch = false;
      this.passConf = passConf;
    }
    else{
      this.passwordsNotMatch = true;
    }
    this.checkAll();
  }

  register(): void{
    this._isLoading = true;

    var user = {'id': 0, 'SuperUser': false, 'Token': '', 'Name': this.name, 'Surname': this.surname, 'Email': this.email, 'Password': this.pass, 'Phone': '', 'Description': '',
    'UserPhoto': '', 'Address': '', 'City': ''};

    this._userService.registerUser(user)
      .subscribe(data=>{
        console.log(data);
        if(data!=null && data!={}){
          let success = this._userService.loginUser(data.Token, data.Name, data.UserPhoto,data.Email, data.Id);
          if(success){
             let nav = this.app.getComponent('nav');
             nav.setRoot(HomePage);
          }
          this._isLoading = false;
        }
      });
  }

  private checkAll(): void{
    if(this.email!='' && this.pass!='' && this.passConf!='' && this.name!='' && this.surname!='' && !this.emailExists && !this.passwordsNotMatch && !this.passLengthNotOk){
      this.allowRegister = true;
    }
    else{
      this.allowRegister = false;
    }
  }


}