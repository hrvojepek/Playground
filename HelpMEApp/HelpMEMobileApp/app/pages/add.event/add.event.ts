import {Page, NavController, IonicApp, ActionSheet} from 'ionic-angular';
import {HumEventService} from "../../services/humEvents-service/hum.event.service";
import {UsersService} from "../../services/user-service/user.service";

@Page({
    templateUrl: 'build/pages/add.event/add-event.html',
    providers: []
})
export class AddEventPage {

    private _isLoading: boolean = false;

    //public uploader:FileUploader = null;
	public hasBaseDropZoneOver:boolean = false;
    public hasAnotherDropZoneOver:boolean = false;

	private subCategoryVisible: boolean = false;
	private userOwnerId: any = '';

	private userEmail: string = '';

	private humEvent: any = null;

	private eventPostedInDB:boolean = false;


    constructor(private nav: NavController, private app: IonicApp, private _humEventService: HumEventService, private _userService: UsersService){
        
    }


	uploadPhotos(): void{
	    //this.uploader.uploadAll();
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
	          //this.uploader = new FileUploader({url: url});
	          this.eventPostedInDB = true;
	        }
	      });
	  }

	  finishAdding(): void{
	    
	  }

	  showDate(start): void{
	    console.log(start);
	  }

}