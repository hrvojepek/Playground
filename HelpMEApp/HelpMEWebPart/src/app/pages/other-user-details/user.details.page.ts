/**
 * Created by Hrvoje on 26.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {SpinnerComponent} from "../../help-components/spinner-component/spinner.component";
import {UserService} from "../../services/user-service/UserService";
import {DonationService} from "../../services/donation-service/DonationService";
import {HumEventService} from "../../services/humEvents-service/HumEventService";

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';


@Component({
  selector: 'my-profile',
  providers: [DonationService, HumEventService],
  pipes: [],
  directives: [SpinnerComponent, ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/other-user-details/userDetails.html',
})

export class UserDetailsPage implements OnInit{

  private _isLoading: boolean = false;
  private _user: any = null;
  private _userDonations: any[] = [];
  private _userEvents: any[] = [];


  constructor(private _userService: UserService, private _router: Router, private _routeParams: RouteParams, private _donationService: DonationService, private _eventService: HumEventService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }
  }

  ngOnInit(): void{
    this._isLoading = true;

    var userID = this._routeParams.get('id');

    this._userService.getUserByID(userID)
      .subscribe( usr =>{
        console.log(usr);
        this._user = usr;

        this._donationService.getDonationsByOwner(this._user.Id)
          .subscribe(dons =>{
            this._userDonations = dons;
            for(var i=0; i<this._userDonations.length;i++){
              this._userDonations[i].TimeCreated = new Date(this._userDonations[i].TimeCreated);
            }


            this._eventService.getEventsByOwner(this._user.Id)
              .subscribe(evnts =>{
                this._userEvents = evnts;
                console.log(this._userEvents);

                for(var j=0; j<this._userEvents.length;j++){
                 this._userEvents[j].StartingTime = new Date(this._userEvents[j].StartingTime);
                 this._userEvents[j].EndingTime = new Date(this._userEvents[j].StartingTime);
                 }
                this._isLoading = false;
              });
          });

      });

  }



}

