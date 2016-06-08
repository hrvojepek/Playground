/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Message} from "./Message";

export class MessageGroup{
  private _id: number;
  private _messages: Message[];


    constructor(id:number, messages:Message[]) {
      this._id = id;
      this._messages = messages;
    }


  get id():number{
      return this._id;
      }

  set id(value:number){
      this._id=value;
      }

  get messages():Message[]{
      return this._messages;
      }

  set messages(value:Message[]){
      this._messages=value;
      }
}
