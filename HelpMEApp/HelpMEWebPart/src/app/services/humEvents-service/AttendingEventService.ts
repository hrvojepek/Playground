/**
 * Created by Hrvoje on 13.06.2016..
 */

import {Injectable} from "@angular/core";
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import 'rxjs/add/operator/map';

@Injectable()
export class AttendingEventService {

  private address:string = 'http://localhost:5000/api/userswhoattendingevent';

  constructor(private _http:Http) {
  }

  addUserToEvent(user: any): any{
    console.log(user);
    let body = JSON.stringify(user);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(res=>res.json());
  }

  deleteUserFromEvent(id): any {
    return this._http.delete(this.address + '/' + id)
      .map(res=>res.json());
  }

}
