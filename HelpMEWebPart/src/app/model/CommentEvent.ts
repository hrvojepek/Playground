/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Comment} from "./Comment";
import {User} from "./User";

export class CommentEvent extends Comment{
  private _eventID: number;


  constructor(id:number, user:User, comment:string, timeCreated:number, donationID:number) {
    super(id, user, comment, timeCreated);
    this._eventID = donationID;
  }


  get eventID():number{
    return this._eventID;
  }

  set eventID(value:number){
    this._eventID=value;
  }
}
