/**
 * Created by Hrvoje on 26.05.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {SpinnerComponent} from "../../help-components/spinner-component/spinner.component";
import {UserService} from "../../services/user-service/UserService";
import {DonationService} from "../../services/donation-service/DonationService";
import {HumEventService} from "../../services/humEvents-service/HumEventService";
import {ConversationService} from "../../services/conversation-service/ConversationService";

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from "ng2-file-upload/ng2-file-upload";


@Component({
  selector: 'my-profile',
  providers: [DonationService, HumEventService, ConversationService],
  pipes: [],
  directives: [SpinnerComponent, ROUTER_DIRECTIVES, FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES],
  templateUrl: 'app/pages/my-profile/myProfile.html',
})

export class MyProfilePage implements OnInit{

  private _isLoading: boolean = false;
  private _user: any = null;
  private _tmpUser: any = null;
  private _userDonations: any[] = [];
  private _userEvents: any[] = [];
  private _messagesLoading: boolean = false;
  private _userConversations: any[] = [];
  private _selectedConversation: any = null;
  private _editingProfile: boolean = false;
  public msg: string = '';
  private _showUpdatePhoto: boolean = false;
  private _editingPhoto: boolean = false;
  private _deletingUser: boolean = false;

  public uploader:FileUploader = null;
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;


  constructor(private _userService: UserService, private _router: Router, private _donationService: DonationService, private _eventService: HumEventService,
              private _conService: ConversationService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }
  }

  ngOnInit(): void{
    this._isLoading = true;

    var userID: string = localStorage.getItem('userId');
    var userName: string = localStorage.getItem('usrName');
    var userEmail: string = localStorage.getItem('usrEmail');

    var url: string = 'http://localhost:5000/api/users/userPhoto/' + userEmail;

    this.uploader = new FileUploader({url: url});

    this._userService.getUserByID(userID)
      .subscribe( usr =>{
        console.log(usr);
        this._user = usr;
        this._tmpUser = usr;
        console.log(this._tmpUser);

        this._donationService.getDonationsByOwner(this._user.Id)
          .subscribe(dons =>{
            this._userDonations = dons;
            for(var i=0; i<this._userDonations.length;i++){
              this._userDonations[i].TimeCreated = new Date(this._userDonations[i].TimeCreated);
            }
            console.log("donations: " + this._userDonations);

            this._eventService.getEventsByOwner(this._user.Id)
              .subscribe(evnts =>{
                this._userEvents = evnts;
                console.log(this._userEvents);

                for(var j=0; j<this._userEvents.length;j++){
                 this._userEvents[j].StartingTime = new Date(this._userEvents[j].StartingTime);
                 this._userEvents[j].EndingTime = new Date(this._userEvents[j].StartingTime);
                 }
                this._isLoading = false;
              });
          });

      });

    this._messagesLoading = true;

    this._conService.getUserConversations(userID)
      .subscribe(convs =>{
        console.log(convs);

        if(convs.length>0){
          this._userConversations = convs;
          for(var i=0; i<this._userConversations.length; i++){
            var conversation = this._userConversations[i];
            var messages = conversation.Messages;

            for(var j=0; j<messages.length;j++){
              messages[j].TimeCreated = new Date(messages[j].TimeCreated);

              if(messages[j].UserSenderName != userName){
                conversation.UserForChat = messages[j].UserSenderName;
              }
            }
            this._userConversations[i].Messages = messages;
          }
          this._selectedConversation = this._userConversations[0];
          this._messagesLoading = false;
        }
        else {
          this._messagesLoading = false;
        }

      });

  }

  postMessageInConversation(): void {
    console.log(this.msg);
    if(this.msg!='' && this.msg!=null){
      var UserSender = this._user.Name;
      var UserSenderPhoto = this._user.UserPhoto;
      var TimeCreated = new Date();
      var ConversationId = this._selectedConversation.Id;
      var MessageText = this.msg;
      var Seen = false;

      var Message = {"ConversationId": ConversationId, "MessageText": MessageText, "Seen": Seen, "TimeCreated": TimeCreated, "UserSenderName": UserSender, "UserSenderPhoto": UserSenderPhoto};
      console.log(Message);

      this._selectedConversation.Messages.push(Message);
      this.msg = '';
    }
  }

  showUpdateUserPopup(): void{
    this._editingProfile = true;
  }

  updateUser(): void{
    this._editingProfile = false;
    this._userService.updateUser(this._user)
      .subscribe(usr=>{
        console.log(usr);
        this._user = usr;
      });
  }

  cancelUpdateUser(): void{
    this._user = this._tmpUser;
    console.log(this._user);
    this._editingProfile = false;
  }

  uploadProfPhoto(): void{
    this.uploader.uploadAll();
    this._editingPhoto = false;

    this._isLoading = true;

    setTimeout(() => {
      this._userService.getUserByID(this._user.Id)
        .subscribe(usr=>{
          this._user = usr;

          console.log(this._user);
          this._userService.updateUserData(this._user.Token, this._user.Name, this._user.UserPhoto, this._user.Email, this._user.Id);
          this._isLoading = false;
        });
    },1500);
  }

  onDeleteClicked(value: string): void{
    if(value==='Yes'){
      this._isLoading = true;
      this._userService.deleteUser(this._user.Id, this._user.Email)
        .subscribe(res =>{
          if(res!=null){
            this._userService.logoutUser();
          }
          this._deletingUser=false;
          this._isLoading = false;
        });
    }
    else{
      this._deletingUser = false;
    }
  }


}

