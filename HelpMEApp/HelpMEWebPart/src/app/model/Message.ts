/**
 * Created by Hrvoje on 24.05.2016..
 */

export class Message{

  public Id: number;
  public Seen: boolean;
  public UserSenderName: string;
  public UserSenderPhoto: string;
  public UserRecieverName: string;
  public UserRecieverPhoto: string;
  public MessageText: string;
  public TimeCreated: any;


  constructor(Id:number, Seen:boolean, UserSenderName:string, UserSenderPhoto:string, UserRecieverName:string, UserRecieverPhoto:string, MessageText:string, TimeCreated:any) {
      this.Id = Id;
      this.Seen = Seen;
      this.UserSenderName = UserSenderName;
      this.UserSenderPhoto = UserSenderPhoto;
      this.UserRecieverName = UserRecieverName;
      this.UserRecieverPhoto = UserRecieverPhoto;
      this.MessageText = MessageText;
      this.TimeCreated = TimeCreated;
      }
}
