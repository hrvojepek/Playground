/**
 * Created by Hrvoje on 02.06.2016..
 */

import {Injectable} from "angular2/core";
import {Http, Headers, RequestOptions, Response} from "angular2/http";
import 'rxjs/add/operator/map';

@Injectable()
export class DonationService {

  private address: string = 'http://localhost:5000/api/donations';

  constructor(private _http: Http){

  }

  getDonations(): any{
    return this._http.get(this.address)
      .map(res => res.json());
  }

  getDonationById(id: string): any{
    return this._http.get(this.address + '/' + id)
      .map(res => res.json());
  }

  getDonationsByOwner(ownerId: string): any{
    return this._http.get(this.address + '/owner/' + ownerId)
      .map(res => res.json());
  }

  addDon(donation: any): any{
    console.log(donation);
    let body = JSON.stringify(donation);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(res=>res.json());
  }

  deleteDonation(id, email): any {
      return this._http.delete(this.address + '/' + id + '/' + email)
        .map(res=>res.json());
  }

}
