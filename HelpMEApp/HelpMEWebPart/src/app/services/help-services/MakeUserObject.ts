/**
 * Created by Hrvoje on 02.06.2016..
 */

import {Injectable} from "@angular/core";
import {User} from "../../model/User";

@Injectable()
export class UserMakeObjectService {


  constructor(){
  }

  makeUser(usr: any): User{
    var user: User = null;

    var donations = usr.Donations;
    var events = usr.Events;
    var eventsAttending = usr.EventsAttending;
    var wantedDonations = usr.WantedDonations;
    //user = new User();

    return user;
  }

}
