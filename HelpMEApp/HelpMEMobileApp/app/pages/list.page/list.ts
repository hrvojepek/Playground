/**
 * Created by Hrvoje on 02.07.2016..
 */

import {Page, NavController, NavParams} from 'ionic-angular';

@Page({
    template: `
        <ion-navbar *navbar>
            <button menuToggle>
                <ion-icon name="menu"></ion-icon>
            </button>
            <ion-title>
                Users
            </ion-title>
        </ion-navbar>
        
        <div *ngIf="_isLoading">
            <my-spinner></my-spinner>
        </div>
        <ion-content *ngIf="!_isLoading" padding>
            <ion-list *ngIf="users.length>0">
              <ion-item *ngFor="#u of users">
                <ion-avatar item-left>
                  <img [src]="u.UserPhoto">
                </ion-avatar>
                <br>
                <h2>{{u.Name}} {{u.Surname}}</h2>
                <p></p>
              </ion-item>
            </ion-list>
            <div *ngIf="users.length==0" style="text-align:center; width:100%;">
                <h3>No users</h3>
            </div>
         </ion-content>
    `,
    providers: []
})
export class ListPage {

    private _isLoading: boolean = false;

    private users: any[] = [];



    constructor(private nav: NavController, private navParams: NavParams){
        this.users = this.navParams.get('users');
        console.log(this.users);
    }



}