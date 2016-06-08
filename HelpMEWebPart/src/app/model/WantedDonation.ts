/**
 * Created by Hrvoje on 02.06.2016..
 */
import {CommentDonation} from "./CommentDonation";

export class WantedDonation{

  public Id: number;
  public DonationId: number;
  public Caption: string;
  public Description: string;
  public Category: string;
  public SubCategory: string;
  public Condition: string;
  public UserOwnerNameSurname: string;
  public UserWhoWantDonationID: number;
  public Photos: string[];
  public Comments: CommentDonation;


  constructor(Id:number, DonationId:number, Caption:string, Description:string, Category:string, SubCategory:string, Condition:string, UserOwnerNameSurname:string, UserWhoWantDonationID:number, Photos:string[], Comments:CommentDonation) {
      this.Id = Id;
      this.DonationId = DonationId;
      this.Caption = Caption;
      this.Description = Description;
      this.Category = Category;
      this.SubCategory = SubCategory;
      this.Condition = Condition;
      this.UserOwnerNameSurname = UserOwnerNameSurname;
      this.UserWhoWantDonationID = UserWhoWantDonationID;
      this.Photos = Photos;
      this.Comments = Comments;
      }
}
