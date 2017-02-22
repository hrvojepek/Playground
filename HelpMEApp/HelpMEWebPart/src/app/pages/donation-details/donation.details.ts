/**
 * Created by Hrvoje on 27.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";
import {DonationService} from "../../services/donation-service/DonationService";
import {WantDonationService} from "../../services/donation-service/WantDonationService";

@Component({
  selector: 'donation-details',
  providers: [DonationService, WantDonationService],
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

  private _showingPeopleWhoWantDonation: boolean = false;
  private _userWantsDonation: boolean = false;
  private _positionOfUser: number;
  private _usersWhoWantDonation: any[] = [];
  private _user: any = null;

  constructor(private _userService: UserService, private _router: Router, private _routeParams: RouteParams, private _wantDonationService: WantDonationService, private _donationService: DonationService){
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
        console.log(this._donation);
        this._usersWhoWantDonation = this._donation.UsersWhoWantDonation;

        for(var i=0; i<this._usersWhoWantDonation.length;i++){
          if(this._usersWhoWantDonation[i].UserId == Number(this._userId)){
            this._userWantsDonation = true;
          }
        }
        this._donation.TimeCreated = new Date(this._donation.TimeCreated);
        this._isLoading = false;
      });

    this._userService.getUserByID(this._userId)
      .subscribe(usr=>{
        this._user = usr;
        console.log(this._user);
      });
  }

  deleteDonationPopup(): void {
    this._deletingDonation = true;
  }

  onDeleteClicked(value: string): void{
    if(value==='Yes'){
      this._donationService.deleteDonation(this._donation.Id, this._donation.Owner.Email)
        .subscribe(res=>{
          console.log(res);
          if(res!=null){
            this._deletingDonation=false;
            this._router.navigate(['Home']);
          }
        });
    }
    else{
      this._deletingDonation = false;
    }
  }

  wantOrNot(): void{
    if(this._userWantsDonation){
      this._usersWhoWantDonation.splice(this._positionOfUser,1);
      this._wantDonationService.dislikeDonation(Number(this._user.Id))
        .subscribe(res =>{
        });
    }
    else {
      var usr = {'Id':0, 'UserId': Number(this._user.Id), 'Name': this._user.Name, 'Surname': this._user.Surname, 'UserPhoto': this._user.UserPhoto, 'WantedDonationId': Number(this._id)};
      this._usersWhoWantDonation.push(usr);
      this._wantDonationService.likeDonation(usr)
        .subscribe(res=>{
        });
    }
    this._userWantsDonation = !this._userWantsDonation;
  }

}
