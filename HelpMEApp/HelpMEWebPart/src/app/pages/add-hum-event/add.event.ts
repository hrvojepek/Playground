/**
 * Created by Hrvoje on 10.06.2016..
 */

import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router-deprecated';
import {UserService} from "../../services/user-service/UserService";

import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from "ng2-file-upload/ng2-file-upload";
import {HumEventService} from "../../services/humEvents-service/HumEventService";

@Component({
  selector: 'add-event',
  providers: [HumEventService],
  pipes: [],
  directives: [FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES],
  templateUrl: 'app/pages/add-hum-event/addEvent.html',
})

export class AddEventPage implements OnInit{

  public uploader:FileUploader = null;
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;

  private subCategoryVisible: boolean = false;
  private userOwnerId: any = '';

  private userEmail: string = '';

  private humEvent: any = null;

  private eventPostedInDB:boolean = false;

  constructor(private _userService: UserService, private _router: Router, private _humEventService: HumEventService){
    if(!this._userService.isUserLoggedIn()){
      this._router.navigate(['Login']);
    }

    this.userOwnerId = localStorage.getItem('userId');
  }

  ngOnInit(): void{
    var userID: string = localStorage.getItem('userId');
    var userName: string = localStorage.getItem('usrName');
    this.userEmail = localStorage.getItem('usrEmail');
  }

  uploadPhotos(): void{
    this.uploader.uploadAll();
  }

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }

  addEvent(caption, description, address, city, start, end): void{

    var ownerId: number = Number(this.userOwnerId);
    var humEvent = {'Id': 0, 'Caption': caption, 'Description': description, 'Address': address,
      'City': city, 'UserOwnerId': ownerId, 'StartingTime': start, 'EndingTime': end};

    this._humEventService.addEvent(humEvent)
      .subscribe(humEvent =>{
        console.log(humEvent);
        this.humEvent = humEvent;
        if(this.humEvent.Id>0){

          var url: string = 'http://localhost:5000/api/events/eventsPhotos/' + this.userEmail + "/" + this.humEvent.Id;
          this.uploader = new FileUploader({url: url});
          this.eventPostedInDB = true;
        }
      });
  }

  finishAdding(): void{
    this._router.navigate(['Home']);
  }

  showDate(start): void{
    console.log(start);
  }

}
