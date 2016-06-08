/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Component} from '@angular/core';
import {UserService} from "../../services/user-service/UserService";
import {ROUTER_DIRECTIVES} from '@angular/router-deprecated';

@Component({
  selector: 'register',
  providers: [],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/register/register.html',
})

export class RegisterPage {

  private emailExists: boolean = false;
  private passLengthNotOk: boolean = false;
  private passwordsNotMatch: boolean = false;
  private allowRegister: boolean = false;

  private email: string = '';
  private pass: string = '';
  private passConf: string = '';
  private name: string = '';
  private surname: string = '';

  constructor(private _userService: UserService){

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
    console.log(this.email + " " + this.pass);

    var user = {'id': 0, 'SuperUser': false, 'Token': '', 'Name': this.name, 'Surname': this.surname, 'Email': this.email, 'Password': this.pass, 'Phone': '', 'Description': '',
    'UserPhoto': '', 'Address': '', 'City': ''};

    this._userService.registerUser(user)
      .subscribe(data=>{
        console.log(data);
        if(data!=null && data!={}){
          this._userService.loginUser(data.Token, data.Name, data.UserPhoto,data.Email, data.Id);
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
