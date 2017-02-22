/**
 * Created by Hrvoje on 27.05.2016..
 */

export class Feedback{
  private _id: number;
  private _userPhoto: string;
  private _userName: string;
  private _feedbackComment: string;


  constructor(id:number, userPhoto:string, userName:string, feedbackComment:string) {
      this._id = id;
      this._userPhoto = userPhoto;
      this._userName = userName;
      this._feedbackComment = feedbackComment;
      }


  get id():number{
      return this._id;
      }

  set id(value:number){
      this._id=value;
      }

  get userPhoto():string{
      return this._userPhoto;
      }

  set userPhoto(value:string){
      this._userPhoto=value;
      }

  get userName():string{
      return this._userName;
      }

  set userName(value:string){
      this._userName=value;
      }

  get feedbackComment():string{
      return this._feedbackComment;
      }

  set feedbackComment(value:string){
      this._feedbackComment=value;
      }
}
