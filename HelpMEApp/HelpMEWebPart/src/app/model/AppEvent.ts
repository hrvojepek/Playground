/**
 * Created by Hrvoje on 24.05.2016..
 */

import {User} from "./User";
import {CommentEvent} from "./CommentEvent";

export class AppEvent{
  private _id: number;
  private _userOwner: User;
  private _usersAttending: User[];
  private _caption: string;
  private _description: string;
  private _photo: string;
  private _timeStart: any;
  private _timeEnd: any;
  private _comments: CommentEvent;


  constructor(id:number, userOwner:User, usersAttending:User[], caption:string, description:string, photo:string, timeStart:any, timeEnd:any, comments:CommentEvent) {
      this._id = id;
      this._userOwner = userOwner;
      this._usersAttending = usersAttending;
      this._caption = caption;
      this._description = description;
      this._photo = photo;
      this._timeStart = timeStart;
      this._timeEnd = timeEnd;
      this._comments = comments;
      }


    get id():number {
      return this._id;
    }

  set id(value:number){
      this._id=value;
      }

  get userOwner():User{
      return this._userOwner;
      }

  set userOwner(value:User){
      this._userOwner=value;
      }

  get usersAttending():User[]{
      return this._usersAttending;
      }

  set usersAttending(value:User[]){
      this._usersAttending=value;
      }

  get caption():string{
      return this._caption;
      }

  set caption(value:string){
      this._caption=value;
      }

  get description():string{
      return this._description;
      }

  set description(value:string){
      this._description=value;
      }

  get photo():string{
      return this._photo;
      }

  set photo(value:string){
      this._photo=value;
      }

  get timeStart():any{
      return this._timeStart;
      }

  set timeStart(value:any){
      this._timeStart=value;
      }

  get timeEnd():any{
      return this._timeEnd;
      }

  set timeEnd(value:any){
      this._timeEnd=value;
      }

  get comments():CommentEvent{
      return this._comments;
      }

  set comments(value:CommentEvent){
      this._comments=value;
      }
}
