/**
 * Created by Hrvoje on 19.6.2016..
 */

import {Page, NavController, IonicApp, NavParams} from 'ionic-angular';
import {AttendingEventService} from "../../services/humEvents-service/attending.event.service";
import {UsersService} from "../../services/user-service/user.service";
import {ListPage} from "../list.page/list";
import {EventCommentsPage} from "../list.page/event.comments";

@Page({
    templateUrl: 'build/pages/event.details/event-details.html',
    providers: []
})
export class EventDetailsPage {

    private _isLoading: boolean = false;

    private _userIsAttending: boolean = false;
    private _usersWhoAttending: any[] = [];
    private _comments: any[] = [];
    private _positionOfUser: number = -1;

    private _user: any = null;
    private _userId: any = '';
    private _userEmail: any = '';

    private humEvent: any = {};

    private futureComment: any = {};


    constructor(private nav: NavController, private _userService: UsersService, private app: IonicApp, private navParams: NavParams, private _eventAttendService: AttendingEventService){
        this.humEvent = this.navParams.get('e');

        this._isLoading = true;

        this._userId = localStorage.getItem('usrId');
        console.log(this._userId);
        this._userEmail = localStorage.getItem('usrEmail');

        this.setEvent();

        this._isLoading = false;
    }

    onPageWillEnter(): void{
        // this._isLoading = true;
    }

    attendOrNot(): void{
        if(this._userIsAttending){
            this._usersWhoAttending.splice(this._positionOfUser,1);
            this._eventAttendService.deleteUserFromEvent(Number(this._user.Id))
                .subscribe(res =>{
                });
        }
        else {
            var usr = {'Id':0, 'UserId': Number(this._user.Id), 'Name': this._user.Name, 'Surname': this._user.Surname, 'UserPhoto': this._user.UserPhoto, 'AppEventId': Number(this.humEvent.Id)};
            this._usersWhoAttending.push(usr);
            this._eventAttendService.addUserToEvent(usr)
                .subscribe(res=>{
                });
        }
        this._userIsAttending = !this._userIsAttending;
    }

    showAttenders(): void{
        this.nav.push(ListPage, {"users": this._usersWhoAttending});
    }

    showComments(): void{
        this.nav.push(EventCommentsPage, {"comments": this._comments, "futureComment": this.futureComment});
    }

    private setEvent(): void{
        if(this.humEvent.UsersWhoAttending!=null){
            this._usersWhoAttending = this.humEvent.UsersWhoAttending;
        }
        if(this.humEvent.Comments!=null){
            this._comments = this.humEvent.Comments;
        }

        var numId = Number(this._userId);

        for(var i=0; i<this._usersWhoAttending.length;i++){
            if(this._usersWhoAttending[i].UserId == numId){
                this._userIsAttending = true;
                this._positionOfUser = i;
                console.log(this._positionOfUser);
                console.log(this._userIsAttending);
            }
        }
        this._isLoading = false;

        this._userService.getUserByID(this._userId)
            .subscribe(usr=>{
                console.log(usr);
                this._user = usr;

                this.futureComment = {"EventID": this.humEvent.Id, "UserName": this._user.Name, "UserSurname": this._user.Surname, "UserPhoto": this._user.UserPhoto, "CommentTxt": ''};
            });
    }

}