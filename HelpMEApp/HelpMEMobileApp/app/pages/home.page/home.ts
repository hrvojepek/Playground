/**
 * Created by Hrvoje on 19.5.2016..
 */

import {Page, NavController, IonicApp, ActionSheet} from 'ionic-angular';
import {DonationService} from "../../services/donation-service/donation.service";
import {HumEventService} from "../../services/humEvents-service/hum.event.service";
import {DonationDetailsPage} from "../donation.details/donation.details";
import {UsersService} from "../../services/user-service/user.service";
import {UserDetailsPage} from "../user.details/user.details";
import {EventDetailsPage} from "../event.details/event.details";
import {AddDonationPage} from "../add.donation/add.donation";
import {AddEventPage} from "../add.event/add.event";

@Page({
    templateUrl: 'build/pages/home.page/home.html',
    providers: []
})
export class HomePage {

    private _isLoading: boolean = false;

    private users: any[] = [];
    private donations: any[] = [];
    private humEvents: any[] = [];

    private showing: boolean[] = [true, false, false];


    constructor(private nav: NavController, private app: IonicApp, private _donationService: DonationService, private _eventService: HumEventService, private _userService: UsersService){
        this._isLoading = true;
        this.getUsers();
        this.getDonations();
        this.getHumEvents();
    }

    onPageWillEnter(): void{

    }

    showDonationDetails(pos): void{
        this.nav.push(DonationDetailsPage, {"d": this.donations[pos]});
    }

    showEventDetails(pos): void{
        this.nav.push(EventDetailsPage, {"e": this.humEvents[pos]});
    }

    showUserDetails(pos): void{
        this.nav.push(UserDetailsPage, {"u": this.users[pos]});
    }

    private getUsers(): void{
       this._userService.getUsers()
            .subscribe(u=>{
                this.users = u;
                console.log(this.users);
            });
    }

    private getDonations(): void{
        this._donationService.getDonations()
            .subscribe(d=>{
                this.donations = d;
                console.log(this.donations);
                this._isLoading = false;
            });
    }

    private getHumEvents(): void{
        this._eventService.getEvents()
            .subscribe(e=>{
                this.humEvents = e;
                for(let i=0;i<this.humEvents.length;i++){
                    var start = new Date(this.humEvents[i].StartingTime);
                    var end = new Date(this.humEvents[i].EndingTime);
                    this.humEvents[i].StartingTime = start;
                    this.humEvents[i].EndingTime = end;
                }
                console.log(this.humEvents);
            });
    }

    changeView(pos: number): void{
        for(let i=0; i<this.showing.length;i++){
            if(i==pos){
                this.showing[i]=true;
            }
            else{
                this.showing[i]=false;
            }
        }
    }

    showAddMenu(): void{
       let actionSheet = ActionSheet.create({
        title: '',
        buttons: [
          {
            text: 'Add Donation',
            role: 'destructive',
            handler: () => {
              this.nav.push(AddDonationPage);
            }
          },{
            text: 'Add Event',
            handler: () => {
              this.nav.push(AddEventPage);
            }
          },{
            text: 'Cancel',
            role: 'cancel',
            handler: () => {
              console.log('Cancel clicked');
            }
          }
        ]
      });
      this.nav.present(actionSheet);
    }

}