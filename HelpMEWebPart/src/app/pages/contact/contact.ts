/**
 * Created by Hrvoje on 26.05.2016..
 */

import {Component} from '@angular/core';


@Component({
  selector: 'contact',
  providers: [],
  pipes: [],
  directives: [],
  templateUrl: 'app/pages/contact/contact.html',
})

export class ContactPage {


  send(name:string, email:string, phone:string, message:string): void{
    console.log(name + " " + email + " " + phone + " " + message);
  }

}

