/**
 * Created by Hrvoje on 03.07.2016..
 */

import {Injectable} from "angular2/core";
import {Http, Headers, RequestOptions, Response} from "angular2/http";
import 'rxjs/add/operator/map';

@Injectable()
export class CommentDonationService {

  private address:string = 'http://localhost:5000/api/donationcomments';

  constructor(private _http:Http) {
  }

  addComment(comment: any): any{
    console.log(comment);
    let body = JSON.stringify(comment);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(res=>res.json());
  }

}
