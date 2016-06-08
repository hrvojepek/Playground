/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, NavController} from 'ionic-angular';
import {UserService} from "../../services/user.service";

@Page({
    templateUrl: 'build/pages/registration.page/registration.html',
    providers: [UserService]
})
export class RegistrationPage {

    private _isLoading: boolean = false;
    private hasErr: boolean = true;
    private passMatch: boolean = true;
    private nameEntered: boolean = false;
    private emailEntered: boolean = false;
    private passOk: boolean = false;
    private passLengthOk: boolean = true;

    constructor(private nav: NavController, private _userService: UserService){
    }


    registerUser(name: string, email: string, pass: string): void {
        console.log(name + email + pass);

    }

    getData():void{
        this._isLoading = true;

        this._userService.getUsers()
            .subscribe(data=>{
                console.log(data);
            });
        this._isLoading = false;
    }




    //Functions for checking inputs
    checkName(name: string): void{
        if(name!=null && name != ''){
            this.nameEntered = true;
        }
        else {
            this.nameEntered = false;
        }
        this.checkValues();
    }
    checkEmail(email: string): void{
        if(email!=null && email != ''){
            this.emailEntered = true;
        }
        else {
            this.emailEntered = false;
        }
        this.checkValues();
    }
    checkPass(pass: string): void{
        this.passLengthOk = false;
        this.passMatch = false;
        if(pass.length>=4 && pass !='' && pass!=null){
            this.passOk = true;
            this.passLengthOk = true;
        }else {
            this.passOk = false;
            this.passLengthOk = false;
        }
        this.checkValues();
    }

    checkPasswords(pass: string, passConf: string): void{
        this.passMatch = false;
        if(pass===passConf){
            this.passMatch = true;
        }
        else{
            this.passMatch = false;
        }
        this.checkValues();
    }


    private checkValues(): void{
        if(this.nameEntered && this.emailEntered && this.passOk && this.passMatch){
            this.hasErr = false;
        }
        else {
            this.hasErr = true;
        }
    }


}