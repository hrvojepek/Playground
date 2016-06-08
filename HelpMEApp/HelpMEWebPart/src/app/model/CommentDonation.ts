/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Comment} from "./Comment";
import {User} from "./User";

export class CommentDonation extends Comment{
  private _donationID: number;


  constructor(id:number, user:User, comment:string, timeCreated:number, donationID:number) {
      super(id, user, comment, timeCreated);
      this._donationID = donationID;
      }


  get donationID():number{
      return this._donationID;
      }

  set donationID(value:number){
      this._donationID=value;
      }
}
