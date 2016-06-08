/**
 * Created by Hrvoje on 24.05.2016..
 */

import {Donation} from "./Donation";
import {AppEvent} from "./AppEvent";
import {WantedDonation} from "./WantedDonation";
import {AppEventAttending} from "./AppEventAttending";

export class User{
  public Id: number;
  public SuperUser: boolean;
  public Token: string;
  public Name: string;
  public Surname: string;
  public Email: string;
  public Password: string;
  public Phone: string;
  public Description: string;
  public UserPhoto: string;

  public Donations: Donation[];
  public Events: AppEvent[];
  public EventsAttending: AppEventAttending[];
  public WantedDonations: WantedDonation[];


  constructor(Id:number, SuperUser:boolean, Token:string, Name:string, Surname:string, Email:string, Password:string, Phone:string, Description:string, UserPhoto:string, Donations:Donation[], Events:AppEvent[], EventsAttending:AppEventAttending[], WantedDonations:WantedDonation[]) {
      this.Id = Id;
      this.SuperUser = SuperUser;
      this.Token = Token;
      this.Name = Name;
      this.Surname = Surname;
      this.Email = Email;
      this.Password = Password;
      this.Phone = Phone;
      this.Description = Description;
      this.UserPhoto = UserPhoto;
      this.Donations = Donations;
      this.Events = Events;
      this.EventsAttending = EventsAttending;
      this.WantedDonations = WantedDonations;
      }
}
