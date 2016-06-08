/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, NavController, IonicApp, MenuController} from 'ionic-angular';

@Page({
    templateUrl: 'build/pages/main.page/main.html',
    providers: []
})
export class MainPage {

    private _isLoading: boolean = false;

    constructor(private nav: NavController, private app: IonicApp, private menu: MenuController){
        this.menu.enable(true);
    }



}