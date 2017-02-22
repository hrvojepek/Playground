/**
 * Created by Hrvoje on 03.06.2016..
 */

import {Injectable} from "@angular/core";
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import 'rxjs/add/operator/map';

@Injectable()
export class HumEventService {

  private address: string = 'http://localhost:5000/api/events';

  constructor(private _http: Http){

  }

  getEvents(): any{
    return this._http.get(this.address)
      .map(res => res.json());
  }

  getEventById(id: string): any{
    return this._http.get(this.address + '/' + id)
      .map(res => res.json());
  }

  getEventsByOwner(ownerId: string): any{
    return this._http.get(this.address + '/owner/' + ownerId)
      .map(res => res.json());
  }

  addEvent(humEvent: any): any{
    console.log(humEvent);
    let body = JSON.stringify(humEvent);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(res=>res.json());
  }

  deleteEvent(id, email): any {
    return this._http.delete(this.address + '/' + id + '/' + email)
      .map(res=>res.json());
  }

}
