using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Data.OleDb;
using System.Configuration;

public partial class Index : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        this.userName.Text = (string)Session["userFName"];
        this.userNameFooter.Text = (string)Session["userFName"];

        String query = null;

        if (User.Identity.IsAuthenticated == false)
        {
            query = "SELECT * FROM galleries where public=true order by numOfLikes desc";
        }
        else
        {
            query = "SELECT * FROM galleries order by numOfLikes desc";
        }

        getTopGalleries(query);
        getComments();
    }

    protected void BttnLogout_Click(object sender, EventArgs e)
    {
        FormsAuthentication.SignOut();
        FormsAuthentication.RedirectToLoginPage();
        Session.RemoveAll();
        Session.Abandon();
    }

    protected void BttnCommentPage_Click(object sender, EventArgs e)
    {
        String fullNameUser = (string)Session["userFName"] + " " + (string)Session["userLName"];
        String userMessage = (string)Session["greetingMessage"];
        String profilePhotoPath = (string)Session["profPicPath"];
        String commentText = this.commentTxtBox.Text;
        int idUser = Convert.ToInt32(Session["userID"]);
        int id = lastCommentID();
        id = id + 1;
        this.errLabelComm.Text = id.ToString() + fullNameUser;

        if (commentTxtBox.Text.Equals(null) || commentTxtBox.Text.Equals(""))
        {
            this.errLabelComm.Text = "Unesite komentar ako želite komentirati!";
        }

        else
        {
            OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
            try
            {
                konekcija.Open();
                OleDbCommand upit = new OleDbCommand("INSERT INTO comments VALUES (@ID, @userFLName, @userMessage, @comment, @userProfilePhoto, @userID)", konekcija);
                upit.Parameters.AddWithValue("@ID", id);
                upit.Parameters.AddWithValue("@userFLName", fullNameUser);
                upit.Parameters.AddWithValue("@userMessage", userMessage);
                upit.Parameters.AddWithValue("@comment", commentText);
                upit.Parameters.AddWithValue("@userProfilePhoto", profilePhotoPath);
                upit.Parameters.AddWithValue("@userID", idUser);

                int broj = upit.ExecuteNonQuery();

                if (broj > 0)
                {
                    this.commentTxtBox.Text = "";
                    Response.Redirect("~/Index.aspx");

                }
                else
                {
                    this.errLabelComm.Text = "Pogreška! Vaš komentar nije unesen!";
                }
            }
            catch { }
            finally
            {
                konekcija.Close();
            }
        }
    }

    private int lastCommentID()
    {
        int lastID = 0;

        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("Select MAX(ID) FROM comments", konekcija);
            lastID = (int)upit.ExecuteScalar();
            //Console.WriteLine(lastID);
        }
        catch { }
        finally
        {
            konekcija.Close();
        }

        return lastID;
    }

    private void getTopGalleries(String query)
    {
        int i = 0;
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();


            while (podatci.Read() && i<4)
            {
                i++;
                String name = podatci["gallName"].ToString();
                String desc = podatci["gallDescription"].ToString();
                String category = podatci["gallCategory"].ToString();
                String publishDate = podatci["publishDate"].ToString();
                String firstPhotoPath = podatci["firstPhotoPath"].ToString();
                String numLikes = podatci["numOfLikes"].ToString();

                LiteralControl beforeImage = new LiteralControl();
                String divStart = @"<div style='margin-bottom: 2%; float:left;' class='col-md-3 top-deal-top'>
                            <div class='top-deal'>
                                <a href='#' class='mask'>";
                beforeImage.Text += divStart;
                this.topGalleriesPlaceHolder.Controls.Add(beforeImage);


                Image im = new Image();
                im.ImageUrl = firstPhotoPath;
                im.CssClass = "img-responsive zoom-img";
                im.Width = 300;
                im.Height = 200;
                this.topGalleriesPlaceHolder.Controls.Add(im);


                LiteralControl afterImage = new LiteralControl();
                String aftImage = @"</a><div class='deal-bottom'><div class='top-deal1'><h5>";

                afterImage.Text += aftImage;

                String addName = name + @"</h5><p><b>Opis: </b>";
                afterImage.Text += addName;

                desc = LimitString(desc, 20);
                String addDesc = desc + @"...</p><p><b>Kategorija: </b> ";
                afterImage.Text += addDesc;

                String addCategory = category + @"</p><p><b>Datum objave: </b>";
                afterImage.Text += addCategory;

                String addPDate = publishDate + @"</p><p><i class='glyphicon glyphicon-thumbs-up'></i> &nbsp;&nbsp;<b>";
                afterImage.Text += addPDate;

                String addLikes = numLikes + @"</b></p></div><div class='top-deal2'>";
                afterImage.Text += addLikes;

                this.topGalleriesPlaceHolder.Controls.Add(afterImage);

                HyperLink link1 = new HyperLink();
                link1.Text = "Pogledaj";
                link1.CssClass = "hvr-sweep-to-right more";
                link1.NavigateUrl = "GallerySingle.aspx?ID=" + podatci["ID"];
                this.topGalleriesPlaceHolder.Controls.Add(link1);


                LiteralControl afterLink = new LiteralControl();
                String divEnd = @"</div><div class='clearfix'></div> </div></div></div>";
                afterLink.Text += divEnd;
                this.topGalleriesPlaceHolder.Controls.Add(afterLink);
                
            }
            podatci.Close();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private void getComments()
    {
        int i = 0;
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            string query = "Select * from comments order by ID desc";
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();


            while (podatci.Read() && i < 3)
            {
                String user = podatci["userFullName"].ToString();
                String userMsg = podatci["userMessage"].ToString();
                String comment = podatci["comment"].ToString();
                String profilePhoto = podatci["userProfilePhoto"].ToString();
                String userID = podatci["userID"].ToString();

                LiteralControl l = new LiteralControl();
                String divStart, addComm, addPhoto, addUser, addQuote, addUID = null;

                if (i == 0 || i == 1)
                {
                    divStart = @"<div class='bottom-in'>
                                <p class='para-in'>";
                    l.Text += divStart;

                    addComm = comment + @"</p>
                                <i class='dolor'></i>
                                <div class='men-grid'>
                                    <a href='ProfileUser.aspx?ID=";
                    l.Text += addComm;

                    addUID = userID + @"' class='men-top'>
                                        <img class='img-responsive men-top' style='width:5em;' src='";
                    l.Text += addUID;

                    addPhoto = profilePhoto + @"'/></a><div class='men'><span>";
                    l.Text += addPhoto;

                    addUser = user + @"</span><p>";
                    l.Text += addUser;

                    addQuote = userMsg + @"</p>
                                    </div>
                                    <div class='clearfix'></div>
                                </div>
                            </div>";
                    l.Text += addQuote;
                    this.CommentsFaS.Controls.Add(l);
                }

                else
                {
                    divStart = @"<div class='bottom-in'>
                                <p class='para-in'>";
                    l.Text += divStart;

                    addComm = comment + @"</p>
                                <i class='dolor'></i>
                                <div class='men-grid'>
                                    <a href='ProfileUser.aspx?ID=";
                    l.Text += addComm;

                    addUID = userID + @"' class='men-top'>
                                        <img class='img-responsive men-top' style='width:5em;' src='";
                    l.Text += addUID;

                    addPhoto = profilePhoto + @"'/></a><div class='men'><span>";
                    l.Text += addPhoto;

                    addUser = user + @"</span><p>";
                    l.Text += addUser;

                    addQuote = userMsg + @"</p>
                                    </div>
                                    <div class='clearfix'></div>
                                </div>
                            </div>";
                    l.Text += addQuote;
                    this.CommentsFaT.Controls.Add(l);
                }
                
                   
                i++;
            }
            podatci.Close();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private String LimitString(String data, int length)
    {
        return (data == null || data.Length < length)
          ? data
          : data.Substring(0, length);
    }

}