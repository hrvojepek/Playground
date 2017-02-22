/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteConfig, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";
import {DonationService} from "../../services/donation-service/DonationService";
import {DonationsComponent} from "./donations-component/donations.component";
import {HumEventsComponent} from "./hum-events-component/hum.events.component";
import {HumEventService} from "../../services/humEvents-service/HumEventService";
import {UsersComponent} from "./users-component/users.component";

@Component({
  selector: 'home-page',
  providers: [DonationService, HumEventService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES, DonationsComponent, HumEventsComponent, UsersComponent],
  templateUrl: 'app/pages/home/home.html',
})

export class HomePage implements OnInit{

  private _donationsSelected: boolean = true;
  private _eventsSelected: boolean = false;
  private _usersSelected: boolean = false;

  constructor(private _userService: UserService, private _router: Router, private _donationService: DonationService, private _eventService: HumEventService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }
  }

  ngOnInit(): void{
  }

  showDonations(): void{
    this._donationsSelected = true;
    this._eventsSelected = false;
    this._usersSelected = false;
  }

  showEvents(): void{
    this._donationsSelected = false;
    this._eventsSelected = true;
    this._usersSelected = false;
  }

  showUsers(): void{
    this._donationsSelected = false;
    this._eventsSelected = false;
    this._usersSelected = true;
  }

}
