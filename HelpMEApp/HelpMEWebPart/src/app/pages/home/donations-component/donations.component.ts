/**
 * Created by Hrvoje on 06.06.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../../services/user-service/UserService";
import {DonationService} from "../../../services/donation-service/DonationService";

@Component({
  selector: 'donations',
  providers: [DonationService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/home/donations-component/donations.html',
})

export class DonationsComponent implements OnInit{

  private _isLoading: boolean = false;
  private _donations: any[] = [];
  private _donationsTmp: any[] = [];

  private _category:string = 'all';
  private _subCategory:string = 'all';

  constructor(private _userService: UserService, private _router: Router, private _donationService: DonationService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }

    this._isLoading = true;

    this._donationService.getDonations()
      .subscribe(dnts => {
        console.log(dnts);
        this._donations = dnts;
        for(var i=0;i<this._donations.length;i++){
          this._donations[i].TimeCreated = new Date(this._donations[i].TimeCreated);
        }

        this._donationsTmp = this._donations;
        this._isLoading = false;
      });
  }

  ngOnInit(): void{
  }

  onCategoryChanged(value: string): void{
    this._category = value;
    this.filterDonations();
  }

  onSubCategoryChanged(value: string): void{
    this._subCategory = value;
  }

  filterDonations(): void{

    var category = this._category;
    var subCategory = this._subCategory;

    this._donations = this._donationsTmp;

    if (category == 'All') {
      this._donations = this._donationsTmp;
    }

    else{
      this._donations = this._donations.filter((v) => {
        if (v.Category.toLowerCase().indexOf(category.toLowerCase()) > -1) {
          return true;
        }
        return false;
      });
    }
  }

  search(query: string): void{
    this._donations = this._donationsTmp;
    if(query == ''){
      this._donations = this._donationsTmp;
    }
    else {
      this._donations = this._donations.filter((v) => {
        if (v.Caption.toLowerCase().indexOf(query.toLowerCase()) > -1) {
          return true;
        }
        return false;
      });
    }
  }

  searchByDesc(query: string): void{
    this._donations = this._donationsTmp;
    if(query == ''){
      this._donations = this._donationsTmp;
    }
    else {
      this._donations = this._donations.filter((v) => {
        if (v.Description.toLowerCase().indexOf(query.toLowerCase()) > -1) {
          return true;
        }
        return false;
      });
    }
  }

  searchByOwnerName(query: string): void{
    this._donations = this._donationsTmp;
    if(query == ''){
      this._donations = this._donationsTmp;
    }
    else {
      this._donations = this._donations.filter((v) => {
        if (v.Owner.Name.toLowerCase().indexOf(query.toLowerCase()) > -1 || v.Owner.Surname.toLowerCase().indexOf(query.toLowerCase()) > -1 ) {
          return true;
        }
        return false;
      });
    }
  }

}
