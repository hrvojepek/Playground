/**
 * Created by Hrvoje on 19.6.2016..
 */

import {Page, NavController, IonicApp, NavParams} from 'ionic-angular';
import {WantDonationService} from "../../services/donation-service/want.donation.service";
import {UsersService} from "../../services/user-service/user.service";
import {ListPage} from "../list.page/list";
import {DonationCommentsPage} from "../list.page/donation.comments";


@Page({
    templateUrl: 'build/pages/donation.details/donation-details.html',
    providers: []
})
export class DonationDetailsPage {

    private _isLoading: boolean = false;

    private donation: any = {};

    private _userWantsDonation: boolean = false;
    private _usersWhoWantDonation: any[] = [];
    private _comments: any[] = [];
    private _positionOfUser: number = -1;

    private _user: any = null;
    private _userId: any = '';
    private _userEmail: any = '';

    private futureComment: any = {};


    constructor(private nav: NavController, private app: IonicApp, private navParams: NavParams, private _wantDonationService: WantDonationService, private _userService: UsersService){
        this._isLoading = true;

        this.donation = this.navParams.get('d');
        console.log(this.donation);
        var d = this.donation.TimeCreated;
        this.donation.TimeCreated = new Date(d);

        this._userId = localStorage.getItem('usrId');
        this._userEmail = localStorage.getItem('usrEmail');

        this.setDonation();

        this._isLoading = false;
    }

    onPageWillEnter(): void{
        // this._isLoading = true;
    }

     wantOrNot(): void{
        if(this._userWantsDonation){
            this._usersWhoWantDonation.splice(this._positionOfUser,1);
            this._wantDonationService.dislikeDonation(Number(this._user.Id))
                .subscribe(res =>{
                });
        }
        else {
            var usr = {'Id':0, 'UserId': Number(this._user.Id), 'Name': this._user.Name, 'Surname': this._user.Surname, 'UserPhoto': this._user.UserPhoto, 'WantedDonationId': Number(this.donation.Id)};
            this._usersWhoWantDonation.push(usr);
            this._wantDonationService.likeDonation(usr)
                .subscribe(res=>{
                });
        }
        this._userWantsDonation = !this._userWantsDonation;
    }

    showUsersWhoWant(): void{
        this.nav.push(ListPage, {"users": this._usersWhoWantDonation});
    }

    showComments(): void{
        this.nav.push(DonationCommentsPage, {"comments": this._comments, "futureComment": this.futureComment});
    }

    private setDonation(): void{
        if(this.donation.UsersWhoWantDonation!=null){
            this._usersWhoWantDonation = this.donation.UsersWhoWantDonation;
        }
        if(this.donation.Comments!=null){
            this._comments = this.donation.Comments;
        }

        var numId = Number(this._userId);

        for(var i=0; i<this._usersWhoWantDonation.length;i++){
            if(this._usersWhoWantDonation[i].UserId == numId){
                this._userWantsDonation = true;
                this._positionOfUser = i;
            }
        }
        this._isLoading = false;

        this._userService.getUserByID(this._userId)
            .subscribe(usr=>{
                console.log(usr);
                this._user = usr;

                this.futureComment = {"DonationID": this.donation.Id, "UserName": this._user.Name, "UserSurname": this._user.Surname, "UserPhoto": this._user.UserPhoto, "CommentTxt": ''};
            });
    }


}