/**
 * Created by Hrvoje on 03.06.2016..
 */

import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
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

  deleteEventById(id: string): void {
    console.log("Deleting");
  }

}
