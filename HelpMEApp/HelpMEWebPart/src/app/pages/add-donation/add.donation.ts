/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from "ng2-file-upload/ng2-file-upload";
import {DonationService} from "../../services/donation-service/DonationService";

@Component({
  selector: 'add-donation',
  providers: [DonationService],
  pipes: [],
  directives: [FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES],
  templateUrl: 'app/pages/add-donation/addDonation.html',
})

export class AddDonationPage implements OnInit{

  public uploader:FileUploader = null;
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;

  private subCategoryVisible: boolean = false;
  private userOwnerId: any = '';

  private userEmail: string = '';

  private donation: any = null;

  private donationPostedInDB:boolean = false;

  constructor(private _userService: UserService, private _router: Router, private _donationService: DonationService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }

    this.userOwnerId = localStorage.getItem('userId');
  }

  ngOnInit(): void{
    var userID: string = localStorage.getItem('userId');
    var userName: string = localStorage.getItem('usrName');
    this.userEmail = localStorage.getItem('usrEmail');
  }

  uploadPhotos(): void{
    this.uploader.uploadAll();
  }

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }

  addDonation(cat, subCat, condition, caption, description, city): void{

    var don = {'Id': 0, 'Caption': caption, 'Description': description, 'Category': cat, 'SubCategory': subCat, 'Condition': condition,
      'City': city, 'OwnerId': this.userOwnerId};

    this._donationService.addDon(don)
      .subscribe(donation =>{
        console.log(donation);
        this.donation = donation;
        if(this.donation.Id>0){
          var url: string = 'http://localhost:5000/api/donations/donationsPhotos/' + this.userEmail + "/" + this.donation.Id;

          this.uploader = new FileUploader({url: url});
          this.donationPostedInDB = true;
        }
      });
  }


  checkCategory(cat): void{
    if(cat=='Chlotes' || cat=='Footwear'){
      this.subCategoryVisible = true;
    }
    else {
      this.subCategoryVisible = false;
    }
  }

  finishAdding(): void{
    this._router.navigate(['Home']);
  }

}
