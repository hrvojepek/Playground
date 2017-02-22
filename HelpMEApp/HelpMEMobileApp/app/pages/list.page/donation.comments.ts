/**
 * Created by Hrvoje on 02.07.2016..
 */

import {Page, NavController, NavParams} from 'ionic-angular';
import {CommentDonationService} from "../../services/donation-service/comment.donation.service";

@Page({
    template: `
        <ion-navbar *navbar>
            <button menuToggle>
                <ion-icon name="menu"></ion-icon>
            </button>
            <ion-title>
                Comments
            </ion-title>
        </ion-navbar>
        
        <div *ngIf="_isLoading">
            <my-spinner></my-spinner>
        </div>
        
        <ion-content *ngIf="!_isLoading" padding id="comments">
            <div>
                <ion-list *ngIf="comments.length>0">
                  <ion-item *ngFor="#c of comments" >
                    <ion-avatar item-left>
                      <img [src]="c.UserPhoto">
                    </ion-avatar>
                    <h2 style="margin-top: 2%;">{{c.UserName}} {{c.UserSurname}}</h2>
                    <p>{{c.CommentTxt}}</p>
                    <ion-note item-right style="font-size:0.6em;">{{c.TimeCreated | date:'yy-MM-dd HH:mm'}}</ion-note>
                  </ion-item>
                </ion-list>
                <div *ngIf="comments.length==0" style="text-align:center; width:100%;">
                    <h3>No comments</h3>
                </div>        
            </div>
        </ion-content>
         
          <div class="commentInput">
               <textarea style="width: 100%; height: 65%;" #c [(ngModel)]="ctxt"></textarea>
               <button style="float: right; margin-right: 5%;" (click)="addComment(c.value)">Comment</button>
          </div>

    `,
    providers: []
})
export class DonationCommentsPage {

    private _isLoading: boolean = false;

    private comments: any[] = [];

    private comment: any = null;

    private ctxt: string = '';



    constructor(private nav: NavController, private navParams: NavParams, private _commentDonationService: CommentDonationService){

        this.comments = this.navParams.get('comments');
        this.comment = this.navParams.get('futureComment');

        this.setComments();

    }

    addComment(c: string): void{
        if(c!=''){
        var comm = {"DonationID": this.comment.DonationID, "UserName": this.comment.UserName, "UserSurname": this.comment.UserSurname, "UserPhoto": this.comment.UserPhoto, "CommentTxt": c};

        this._commentDonationService.addComment(comm)
            .subscribe(res=>{
                console.log(res);
            });
        var comm2 = {"DonationID": this.comment.DonationID, "UserName": this.comment.UserName, "UserSurname": this.comment.UserSurname, "UserPhoto": this.comment.UserPhoto, "TimeCreated": Date.now(), "CommentTxt": c};
        this.comments.push(comm2);
        console.log(this.comments);
        this.ctxt = '';
        }
    }

    private setComments(): void{
        for(let i=0; i<this.comments.length;i++){
            this.comments[i].TimeCreated = new Date(this.comments[i].TimeCreated);
        }
    }

}
