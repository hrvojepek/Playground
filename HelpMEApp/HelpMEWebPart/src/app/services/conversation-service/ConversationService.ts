/**
 * Created by Hrvoje on 03.06.2016..
 */

import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import 'rxjs/add/operator/map';

@Injectable()
export class ConversationService {

  private address: string = 'http://localhost:5000/api/conversations';

  constructor(private _http: Http){

  }

  getConversations(): any{
    return this._http.get(this.address)
      .map(res => res.json());
  }

  getConversationById(id: string): any{
    return this._http.get(this.address + '/' + id)
      .map(res => res.json());
  }

  getUserConversations(userid: string): any{
    return this._http.get(this.address + '/userid/' + userid)
      .map(res => res.json());
  }

  deleteConversationById(id: string): void {
    console.log("Deleting");
  }

}
