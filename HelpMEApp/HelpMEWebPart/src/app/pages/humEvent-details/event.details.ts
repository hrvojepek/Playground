/**
 * Created by Hrvoje on 27.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";
import {HumEventService} from "../../services/humEvents-service/HumEventService";
import {AttendingEventService} from "../../services/humEvents-service/AttendingEventService";

@Component({
  selector: 'event-details',
  providers: [HumEventService, AttendingEventService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/humEvent-details/event-details.html',
})

export class HumEventDetailsPage implements OnInit{

  private _isLoading: boolean = false;
  private _deletingEvent: boolean = false;
  private _id: any = 0;
  private _userId = '';
  private _userEmail = '';
  private _humEvent: any = null;
  private _usersWhoAttending: any[] = [];
  private _showingAttenders: boolean = false;
  private _userIsAttending: boolean = false;
  private _positionOfUser: number = 0;
  private _user: any = null;

  constructor(private _userService: UserService, private _router: Router, private _routeParams: RouteParams, private _eventService: HumEventService, private _eventAttendService: AttendingEventService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }
  }

  ngOnInit(): void{
    this._isLoading = true;
    this._userId = localStorage.getItem('userId');
    this._userEmail = localStorage.getItem('usrEmail');
    this._id = this._routeParams.get('id');

    this._eventService.getEventById(this._id)
      .subscribe(evnt=>{
        this._humEvent = evnt;
        if(evnt.UsersWhoAttending!=null){
          this._usersWhoAttending = evnt.UsersWhoAttending;
        }
        console.log(this._usersWhoAttending);

        this._humEvent.StartingTime = new Date(this._humEvent.StartingTime);
        this._humEvent.EndingTime = new Date(this._humEvent.EndingTime);

        console.log(this._humEvent);

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
      });

    this._userService.getUserByID(this._userId)
      .subscribe(usr=>{
        console.log(usr);
        this._user = usr;
      });

  }

  deleteEventPopup(): void {
    this._deletingEvent = true;
  }

  onDeleteClicked(value: string): void{
    if(value==='Yes'){
      this._isLoading = true;
      this._eventService.deleteEvent(this._humEvent.Id, this._userEmail)
        .subscribe(res=>{

          this._deletingEvent=false;
          this._isLoading = true;
          this._router.navigate(['Home']);
        });

    }
    else{
      this._deletingEvent = false;
    }
  }

  showAttenders(): void{
    this._showingAttenders = !this._showingAttenders;
  }

  attendOrNot(): void{
    if(this._userIsAttending){
      this._usersWhoAttending.splice(this._positionOfUser,1);
      this._eventAttendService.deleteUserFromEvent(Number(this._user.Id))
        .subscribe(res =>{
        });
    }
    else {
      var usr = {'Id':0, 'UserId': Number(this._user.Id), 'Name': this._user.Name, 'Surname': this._user.Surname, 'UserPhoto': this._user.UserPhoto, 'AppEventId': Number(this._id)};
      this._usersWhoAttending.push(usr);
      this._eventAttendService.addUserToEvent(usr)
        .subscribe(res=>{
        });
    }
    this._userIsAttending = !this._userIsAttending;
  }

}
