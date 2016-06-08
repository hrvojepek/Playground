/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, RouteConfig, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";

@Component({
  selector: 'my-header',
  providers: [],
  pipes: [],
  directives: [ROUTER_DIRECTIVES],
  templateUrl: 'app/help-components/header/header.html',
})

export class HeaderComponent implements OnInit{

  private _userName: string = '';
  private _userPhoto: string = '';
  private _userEmail: string = '';
  private _isLoading: boolean = false;
  private _profileMenuOpened: boolean = false;
  private _isUserLoggedIn: boolean = false;

  constructor(private _userService: UserService, private _router: Router){
    this._userService.userHasLoggedIn.subscribe(usr => this.setUserDataF(usr));
    this._userService.userHasChangedData.subscribe(usr => this.setUserData(usr));
  }

  ngOnInit(): void{
    this._isLoading = true;
    if(localStorage.getItem('token')!=null){
      this.setUserData('s');
    }
    else {
      this._isUserLoggedIn = false;
    }

    this._isLoading = false;
  }

  setUserData(user: any): void{
    this._isUserLoggedIn = true;
    this._userName = localStorage.getItem('usrName');
    this._userPhoto = localStorage.getItem('usrPhoto');
    this._userEmail = localStorage.getItem('usrEmail');
  }

  logoutUser(): void{
    console.log("logoutam");
    this._isUserLoggedIn = false;
    this.openCloseMenu();
    this._userService.logoutUser();
  }

  openCloseMenu(): void{
    this._profileMenuOpened = !this._profileMenuOpened;
  }

  setUserDataF(usr): void{
    this._isUserLoggedIn = true;
    this._userName = localStorage.getItem('usrName');
    this._userPhoto = localStorage.getItem('usrPhoto');
    this._userEmail = localStorage.getItem('usrEmail');
    this._router.navigate(['Home']);
  }
}
