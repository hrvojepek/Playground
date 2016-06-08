/**
 * Created by Hrvoje on 06.06.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../../services/user-service/UserService";
import {HumEventService} from "../../../services/humEvents-service/HumEventService";

@Component({
  selector: 'humevents',
  providers: [HumEventService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/home/hum-events-component/humEvents.html',
})

export class HumEventsComponent implements OnInit{

  private _isLoadingEvents: boolean = false;
  private _humEvents: any[] = [];
  private _humEventsTmp: any[] = [];

  constructor(private _userService: UserService, private _router: Router, private _eventService: HumEventService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }

    this._isLoadingEvents = true;

    this._eventService.getEvents()
      .subscribe(evnts => {
        console.log(evnts);
        this._humEvents = evnts;

        for(var i=0;i<this._humEvents.length;i++){
          var start = new Date(this._humEvents[i].StartingTime);
          var end = new Date(this._humEvents[i].EndingTime);
          this._humEvents[i].StartingTime = start;
          this._humEvents[i].EndingTime = end;
        }
        this._humEventsTmp = this._humEvents;
        this._isLoadingEvents = false;
      });
  }

  ngOnInit(): void{
  }

  search(query: string): void{
    this._humEvents = this._humEventsTmp;
    if(query == ''){
      this._humEvents = this._humEventsTmp;
    }
    else {
      this._humEvents = this._humEvents.filter((v) => {
        if (v.Caption.toLowerCase().indexOf(query.toLowerCase()) > -1) {
          return true;
        }
        return false;
      });
    }
  }

}
