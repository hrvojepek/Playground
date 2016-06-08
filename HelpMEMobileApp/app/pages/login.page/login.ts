/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, NavController, IonicApp} from 'ionic-angular';
import {MainPage} from "../main.page/main";

@Page({
    templateUrl: 'build/pages/login.page/login.html',
    providers: []
})
export class LoginPage {

    private _isLoading: boolean = false;

    constructor(private nav: NavController, private app: IonicApp){

    }


    loginUser(email, password){
        this._isLoading = true;
        var loginSuccessful = false;

        if(email ==="hrvoje@mail.com" && password ==="pass"){
            loginSuccessful = true;
        }



        if(loginSuccessful){
            let nav = this.app.getComponent('nav');
            nav.setRoot(MainPage);
        }
        this._isLoading = false;
    }


}