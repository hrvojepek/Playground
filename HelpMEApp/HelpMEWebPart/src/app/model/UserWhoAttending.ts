/**
 * Created by Hrvoje on 02.06.2016..
 */

export class UserWhoAttending{

  public Id: number;
  public Name: string;
  public Surname: string;
  public UserPhoto: string;


    constructor(Id:number, Name:string, Surname:string, UserPhoto:string) {
      this.Id = Id;
      this.Name = Name;
      this.Surname = Surname;
      this.UserPhoto = UserPhoto;
    }
}
