/**
 * Created by Hrvoje on 26.05.2016..
 */

import {Component} from '@angular/core';
import {UserService} from "../../services/user-service/UserService";
import {AddFeedbackPopupComponent} from "../../help-components/feedback-add-comment/add.feedback.comment";
import {FeedbackService} from "../../services/feedback-service/FeedbackService";


@Component({
  selector: 'feedback',
  providers: [FeedbackService],
  pipes: [],
  directives: [AddFeedbackPopupComponent],
  templateUrl: 'app/pages/feedback/feedback.html',
})

export class FeedbackPage {

  private _isUserLoggedIn: boolean = false;
  private _commentPopupShowing: boolean = false;
  private _feedbacks: any[] = [];

  constructor(private _userService: UserService, private _feedbackService: FeedbackService){
    if(this._userService.isUserLoggedIn()){
      this._isUserLoggedIn = true;
    }

    this._feedbackService.getFeedbacks()
      .subscribe(fds=>{
        console.log(fds);
        this._feedbacks = fds;
      });

  }

  addComment($event): void {
    if($event!='' && $event.length>5){
      var username = localStorage.getItem('usrName');
      var userPhoto = localStorage.getItem('usrPhoto');

      var Feedback = {"Id":0, "FeedbackComment": $event, "UserName": username, "UserPhoto": userPhoto};

      this._feedbacks.push(Feedback);

      this._feedbackService.postFeedback(Feedback)
        .subscribe(data=>{
          console.log(data);
        });
    }
    this._commentPopupShowing = false;
  }

  commentPopupShowHide(): void{
    this._commentPopupShowing = !this._commentPopupShowing;
  }

}

