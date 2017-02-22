import {Page, NavController, IonicApp, ActionSheet} from 'ionic-angular';
import {DonationService} from "../../services/donation-service/donation.service";
import {UsersService} from "../../services/user-service/user.service";
import {HomePage} from "../home.page/home";

@Page({
    templateUrl: 'build/pages/add.donation/add-donation.html',
    providers: []
})
export class AddDonationPage {

    private _isLoading: boolean = false;

    //public uploader:FileUploader = null;

	public hasBaseDropZoneOver:boolean = false;
	public hasAnotherDropZoneOver:boolean = false;

	private subCategoryVisible: boolean = false;
	private userOwnerId: any = '';

	private userEmail: string = '';

	private donation: any = null;

	private donationPostedInDB:boolean = false;


    constructor(private nav: NavController, private app: IonicApp, private _donationService: DonationService, private _userService: UsersService){
        this.userOwnerId = localStorage.getItem('userId');
        this.userEmail = localStorage.getItem('usrEmail');
    }

    uploadPhotos(): void{
    //this.uploader.uploadAll();
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

          //this.uploader = new FileUploader({url: url});
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
    this.nav.push(HomePage);
  }


}