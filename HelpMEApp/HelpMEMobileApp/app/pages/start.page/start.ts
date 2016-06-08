/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, NavController, MenuController} from 'ionic-angular';
import {LoginPage} from "../login.page/login";
import {RegistrationPage} from "../registration.page/registration";

@Page({
    templateUrl: 'build/pages/start.page/start.html',
    providers: []
})
export class StartPage {

    constructor(private nav: NavController, private menu: MenuController){
        this.menu.enable(false);
    }

    goToRegistration(): void{
        this.nav.push(RegistrationPage);
    }

    goToLogin(): void {
        this.nav.push(LoginPage);
    }

    facebookLogin(): void{
        console.log("FAcebook login");
    }

}