/**
 * Created by Hrvoje on 06.06.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../../services/user-service/UserService";
import {HumEventService} from "../../../services/humEvents-service/HumEventService";

@Component({
  selector: 'users',
  providers: [UserService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/pages/home/users-component/users.html',
})

export class UsersComponent implements OnInit{

  private _isLoadingUsers: boolean = false;
  private _users: any[] = [];
  private _usersTmp: any[] = [];

  constructor(private _userService: UserService, private _router: Router){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }

    var loggedUserID = localStorage.getItem('userId');

    this._isLoadingUsers = true;

    this._userService.getUsers()
      .subscribe(usrs => {
        console.log(usrs);
        this._users = usrs;
        this._usersTmp = usrs;
        for(var i=0;i<this._users.length;i++){
          if(this._users[i].Id == loggedUserID){
            this._users.splice(i,1);
          }
        }

        this._isLoadingUsers = false;
      });
  }

  ngOnInit(): void{
  }

  search(query: string): void{
    this._users = this._usersTmp;
    if(query == ''){
      this._users = this._usersTmp;
    }
    else {
      this._users = this._users.filter((v) => {
        if (v.Name.toLowerCase().indexOf(query.toLowerCase()) > -1 || v.Surname.toLowerCase().indexOf(query.toLowerCase()) > -1) {
          return true;
        }
        return false;
      });
    }
  }

}
