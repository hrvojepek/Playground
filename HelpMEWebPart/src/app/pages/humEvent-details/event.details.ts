/**
 * Created by Hrvoje on 27.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteParams, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";
import {HumEventService} from "../../services/humEvents-service/HumEventService";

@Component({
  selector: 'event-details',
  providers: [HumEventService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/humEvent-details/event-details.html',
})

export class HumEventDetailsPage implements OnInit{

  private _isLoading: boolean = false;
  private _deletingEvent: boolean = false;
  private _id: any = 0;
  private _userId = '';
  private _humEvent: any = null;
  private _usersWhoAttending: any[] = [];

  constructor(private _userService: UserService, private _router: Router, private _routeParams: RouteParams, private _eventService: HumEventService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }
  }

  ngOnInit(): void{
    this._isLoading = true;
    this._userId = localStorage.getItem('userId');
    this._id = this._routeParams.get('id');

    this._eventService.getEventById(this._id)
      .subscribe(evnt=>{
        this._humEvent = evnt;
        if(evnt.UsersWhoAttending!=null){
          this._usersWhoAttending = evnt.UsersWhoAttending;
        }

        this._humEvent.StartingTime = new Date(this._humEvent.StartingTime);
        this._humEvent.EndingTime = new Date(this._humEvent.EndingTime);

        console.log(this._humEvent);
        this._isLoading = false;
      });

  }

  deleteEventPopup(): void {
    this._deletingEvent = true;
  }

  onDeleteClicked(value: string): void{
    if(value==='Yes'){
      console.log("Obrisat cu");
      this._deletingEvent=false;
    }
    else{
      this._deletingEvent = false;
    }
  }

}
