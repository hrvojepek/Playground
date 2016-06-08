import {User} from "./User";
/**
 * Created by Hrvoje on 24.05.2016..
 */

export class Comment{
  private _id: number;
  private _user: User;
  private _comment: string;
  private _timeCreated: any;


  constructor(id:number, user:User, comment:string, timeCreated:any) {
      this._id = id;
      this._user = user;
      this._comment = comment;
      this._timeCreated = timeCreated;
      }


  get id():number{
      return this._id;
      }

  set id(value:number){
      this._id=value;
      }

  get user():User{
      return this._user;
      }

  set user(value:User){
      this._user=value;
      }

  get comment():string{
      return this._comment;
      }

  set comment(value:string){
      this._comment=value;
      }

  get timeCreated():any{
      return this._timeCreated;
      }

  set timeCreated(value:any){
      this._timeCreated=value;
      }
}
