/**
 * Created by Hrvoje on 25.6.2016..
 */

import {Page, NavController, IonicApp, NavParams} from 'ionic-angular';
import {DonationService} from "../../services/donation-service/donation.service";
import {HumEventService} from "../../services/humEvents-service/hum.event.service";

@Page({
    templateUrl: 'build/pages/user.details/user-details.html',
    providers: []
})
export class UserDetailsPage {

    private _isLoading: boolean = false;

    private user: any = {};

    private humEvents: any[] = [];
    private donations: any[] = [];


    constructor(private nav: NavController, private app: IonicApp, private navParams: NavParams, private _donationService: DonationService, private _eventService: HumEventService){
        this.user = this.navParams.get('u');
        console.log(this.user);

        this._donationService.getDonationsByOwner(this.user.Id)
          .subscribe(dons =>{
            this.donations = dons;
            for(var i=0; i<this.donations.length;i++){
              this.donations[i].TimeCreated = new Date(this.donations[i].TimeCreated);
            }
            console.log(this.donations);


            this._eventService.getEventsByOwner(this.user.Id)
              .subscribe(evnts =>{
                this.humEvents = evnts;
                console.log(this.humEvents);

                for(var j=0; j<this.humEvents.length;j++){
                 this.humEvents[j].StartingTime = new Date(this.humEvents[j].StartingTime);
                 this.humEvents[j].EndingTime = new Date(this.humEvents[j].StartingTime);
                 }
                this._isLoading = false;
              });
          });
    }

    onPageWillEnter(): void{
        // this._isLoading = true;
    }


}