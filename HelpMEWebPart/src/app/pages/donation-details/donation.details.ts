/**
 * Created by Hrvoje on 27.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";
import {DonationService} from "../../services/donation-service/DonationService";

@Component({
  selector: 'donation-details',
  providers: [DonationService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/donation-details/donation-details.html',
})

export class DonationDetailsPage implements OnInit{

  private _isLoading: boolean = false;
  private _deletingDonation: boolean = false;
  private _id: any = 0;
  private _userId = '';
  private _donation: any = null;

  constructor(private _userService: UserService, private _router: Router, private _routeParams: RouteParams, private _donationService: DonationService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }
  }

  ngOnInit(): void{
    this._isLoading = true;
    this._userId = localStorage.getItem('userId');
    this._id = this._routeParams.get('id');

    this._donationService.getDonationById(this._id)
      .subscribe(dnt=>{
        this._donation = dnt;
        this._donation.TimeCreated = new Date(this._donation.TimeCreated);
        this._isLoading = false;
      });
  }

  deleteDonationPopup(): void {
    this._deletingDonation = true;
  }

  onDeleteClicked(value: string): void{
    if(value==='Yes'){
      this._deletingDonation=false;
    }
    else{
      this._deletingDonation = false;
    }
  }

}
