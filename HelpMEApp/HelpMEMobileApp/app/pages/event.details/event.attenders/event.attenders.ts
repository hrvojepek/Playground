/**
 * Created by Hrvoje on 28.06.2016..
 */

import {Page, NavController, IonicApp, NavParams} from 'ionic-angular';

@Page({
    templateUrl: 'build/pages/event.details/event.attenders/event-attenders.html',
    providers: []
})
export class DonationDetailsPage {

    private _isLoading: boolean = false;

    private users: any = {};


    constructor(private nav: NavController, private app: IonicApp, private navParams: NavParams){
        this.users = this.navParams.get('usrs');
    }

    onPageWillEnter(): void{
        // this._isLoading = true;
    }


}