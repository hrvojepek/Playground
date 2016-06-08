/**
 * Created by Hrvoje on 24.05.2016..
 */

import {User} from "./User";
import {CommentDonation} from "./CommentDonation";

export class Donation{

  private _id: number;
  private _owner: User;
  private _timeCreated: any;
  private _caption: string;
  private _description: string;
  private _category: string;
  private _peopleWhoNeed: User[];
  private _subCategory: string;
  private _condition: string;
  private _comments: CommentDonation[];
  private _photos: string[];


    constructor(id:number, owner:User, timeCreated:any, caption:string, description:string, category:string, peopleWhoNeed:User[], subCategory:string, condition:string, comments:CommentDonation[], photos:string[]) {
      this._id = id;
      this._owner = owner;
      this._timeCreated = timeCreated;
      this._caption = caption;
      this._description = description;
      this._category = category;
      this._peopleWhoNeed = peopleWhoNeed;
      this._subCategory = subCategory;
      this._condition = condition;
      this._comments = comments;
      this._photos = photos;
    }


    get subCategory():string {
      return this._subCategory;
    }

  set subCategory(value:string){
      this._subCategory=value;
      }

  get condition():string{
      return this._condition;
      }

  set condition(value:string){
      this._condition=value;
      }

  get id():number{
      return this._id;
      }

  get photos():string[]{
      return this._photos;
      }

  set photos(value:string[]){
      this._photos=value;
      }

  set id(value:number){
      this._id=value;
      }

  get owner():User{
      return this._owner;
      }

  set owner(value:User){
      this._owner=value;
      }

  get timeCreated():any{
      return this._timeCreated;
      }

  set timeCreated(value:any){
      this._timeCreated=value;
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

  get category():string{
      return this._category;
      }

  set category(value:string){
      this._category=value;
      }

  get peopleWhoNeed():User[]{
      return this._peopleWhoNeed;
      }

  set peopleWhoNeed(value:User[]){
      this._peopleWhoNeed=value;
      }

  get comments():CommentDonation[]{
      return this._comments;
      }

  set comments(value:CommentDonation[]){
      this._comments=value;
      }
}
