/**
 * Created by Hrvoje on 03.06.2016..
 */

import {Injectable} from "@angular/core";
import {Http, Headers, RequestOptions, Response} from "@angular/http";
import 'rxjs/add/operator/map';

@Injectable()
export class FeedbackService {

  private address: string = 'http://localhost:5000/api/feedbacks';

  constructor(private _http: Http){

  }

  getFeedbacks(): any{
    return this._http.get(this.address)
      .map(res => res.json());
  }

  postFeedback(feedback: any): any{
    console.log(feedback);
    let body = JSON.stringify(feedback);
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });

    return this._http.post(this.address, body, options)
      .map(this.extractData);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body.data || { };
  }

}
