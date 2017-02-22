/**
 * Created by Hrvoje on 02.07.2016..
 */

import {Page, NavController, NavParams} from 'ionic-angular';
import {CommentEventService} from "../../services/humEvents-service/comment.event.service";

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
export class EventCommentsPage {

    private _isLoading: boolean = false;

    private comments: any[] = [];

    private comment: any = null;

    private ctxt: string = '';



    constructor(private nav: NavController, private navParams: NavParams, private _commentEventService: CommentEventService){

        this.comments = this.navParams.get('comments');
        this.comment = this.navParams.get('futureComment');

        this.setComments();

    }

    addComment(c: string): void{
        if(c!=''){
        var comm = {"EventID": this.comment.EventID, "UserName": this.comment.UserName, "UserSurname": this.comment.UserSurname, "UserPhoto": this.comment.UserPhoto, "CommentTxt": c};
        var comm1 = {"EventID": this.comment.EventID, "UserName": this.comment.UserName, "UserSurname": this.comment.UserSurname, "UserPhoto": this.comment.UserPhoto, "TimeCreated": Date.now(), "CommentTxt": c};
        this.comments.push(comm1);
        this._commentEventService.addComment(comm)
            .subscribe(res=>{
                console.log(res);
            });
        this.ctxt = '';
        }
    }

    private setComments(): void{
        for(let i=0; i<this.comments.length;i++){
            this.comments[i].TimeCreated = new Date(this.comments[i].TimeCreated);
        }
    }

}
