/**
 * Created by Hrvoje on 15.06.2016..
 */

import {Injectable} from "angular2/core";
import {Http, Headers, RequestOptions, Response} from "angular2/http";
import 'rxjs/add/operator/map';

@Injectable()
export class WantDonationService {

  private address:string = 'http://localhost:5000/api/userswhowantdonation';

  constructor(private _http:Http) {
  }

  likeDonation(user: any): any{
    console.log(user);
    let body = JSON.stringify(user);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(res=>res.json());
  }

  dislikeDonation(id): any {
    return this._http.delete(this.address + '/' + id)
      .map(res=>res.json());
  }

}
