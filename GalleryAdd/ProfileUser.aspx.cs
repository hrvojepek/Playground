using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Data.OleDb;
using System.Configuration;
using System.IO;
using System.Data;

public partial class ProfileUser : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (User.Identity.IsAuthenticated == false)
        {
            Response.Redirect("~/Login.aspx");
        }

        this.userName.Text = (string)Session["userFName"];
        this.userNameFooter.Text = (string)Session["userFName"];

        int idUser = 0;
        //get UserID from url or if it has no url parameters then from session
        if (Request.QueryString != null && Request.QueryString.Count > 0) 
        {
            idUser = Convert.ToInt16(Request.QueryString["ID"]);

        }
        else
        {
            idUser = Convert.ToInt32(Session["userID"]);
        }

        if (idUser == Convert.ToInt32(Session["userID"]) || Request.QueryString == null && Request.QueryString.Count == 0)
        {
            this.BttnAddGallery.Visible = true;
            this.BttnDeactivate.Visible = true;
        }
        else
        {
            this.BttnAddGallery.Visible = false;
            this.BttnDeactivate.Visible = false;
        }

        //load user informations and put it in aspx
        getUserInformations(idUser);

        bool hasGalleries = false;

        //Load user's galleries
        hasGalleries = getUserGalleries(idUser);

        if (!hasGalleries) 
        {
            this.noGalleries.Visible = true;
        }

    }

    protected void BttnLogout_Click(object sender, EventArgs e)
    {
        FormsAuthentication.SignOut();
        FormsAuthentication.RedirectToLoginPage();
        Session.RemoveAll();
        Session.Abandon();

    }

    protected void BttnDeactivate_Click(object sender, EventArgs e)
    {
        int id = Convert.ToInt32(Session["userID"]);
        String email = (string)Session["email"];
        string path = Server.MapPath("~/users/" + email);

        FormsAuthentication.SignOut();
        Session.RemoveAll();
        Session.Abandon();
        FormsAuthentication.RedirectToLoginPage();

        deleteDirectory(path);
        deleteUserLikes(id);
        deleteUserComments(id);
        deleteUserGalleries(id);
        deleteUser(id);
    }

    protected void BttnAddGallery_Click(object sender, EventArgs e)
    {
        Response.Redirect("~/AddGallery.aspx");
    }

    private bool getUserGalleries(int userID)
    {
        bool hasGalleries = false;
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            string query = "Select * from galleries where userID=" + userID;
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();


            while (podatci.Read())
            {
                hasGalleries = true;
                String id = podatci["ID"].ToString();
                String name = podatci["gallName"].ToString();
                String desc = podatci["gallDescription"].ToString();
                String category = podatci["gallCategory"].ToString();
                String publishDate = podatci["publishDate"].ToString();
                String firstPhotoPath = podatci["firstPhotoPath"].ToString();
                String numLikes = podatci["numOfLikes"].ToString();
                String photos = podatci["photos"].ToString();

                List<String> allGalleryPhotos = photos.Split(';').ToList();
                String firstPhoto = allGalleryPhotos.ElementAt(0);
                String photoPath = "~/users/" + this.userEmailLbl.Text + "/Galleries/" + id + "/" + firstPhoto;

                LiteralControl beforeImage = new LiteralControl();
                String divStart = @"<div style='margin-bottom: 2%; float:left;' class='col-md-3 top-deal-top'>
                            <div class='top-deal'>
                                <a href='#' class='mask'>";
                beforeImage.Text += divStart;
                this.Gallery.Controls.Add(beforeImage);


                Image im = new Image();
                im.ImageUrl = photoPath;
                im.CssClass = "img-responsive zoom-img";
                im.Width = 300;
                im.Height = 200;
                this.Gallery.Controls.Add(im);


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

                this.Gallery.Controls.Add(afterImage);

                HyperLink link1 = new HyperLink();
                link1.Text = "Pogledaj";
                link1.CssClass = "hvr-sweep-to-right more";
                link1.NavigateUrl = "GallerySingle.aspx?ID=" + podatci["ID"];
                this.Gallery.Controls.Add(link1);


                LiteralControl afterLink = new LiteralControl();
                String divEnd = @"</div><div class='clearfix'></div> </div></div></div>";
                afterLink.Text += divEnd;
                this.Gallery.Controls.Add(afterLink);

            }
            podatci.Close();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }
        return hasGalleries;
    }

    private void getUserInformations(int userID)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            string query = "Select * from users where ID=" + userID;
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();


            while (podatci.Read())
            {
                this.nameAndSurnameLbl.Text = podatci["firstName"].ToString() + " " + podatci["lastName"].ToString();
                this.userSexLbl.Text = podatci["sex"].ToString();
                this.userEmailLbl.Text = podatci["email"].ToString();
                this.greetMsgLbl.Text = podatci["greetingMessage"].ToString();
                this.numOfGalleriesLbl.Text = podatci["numOfGalleries"].ToString();

                LiteralControl profPic = new LiteralControl();
                String beforeImg=@"<img src='";
                profPic.Text+=beforeImg;
                String photoPathAndOther = podatci["profilePhoto"].ToString() + @"' 
                    style='border-radius: 10px; max-width: 250px; max-height: 250px; 
                    min-height: 200px; min-width: 200px;' />";
                profPic.Text+=photoPathAndOther;
                this.ProfilePicturePHolder.Controls.Add(profPic);
            }
            podatci.Close();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }
       
    }

    private void deleteUser(int id)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("DELETE FROM users WHERE ID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", id);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelErrorDelete.Text = "Brisanje uspješno!";
            }
            else
            {
                this.labelErrorDelete.Text = "Pogreška kod brisanja Korisnika!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private void deleteUserGalleries(int id)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("DELETE FROM galleries WHERE userID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", id);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelErrorDelete.Text = "Brisanje uspješno!";
            }
            else
            {
                this.labelErrorDelete.Text = "Pogreška kod brisanja galerija Korisnika!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private void deleteUserComments(int id)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("DELETE FROM comments WHERE userID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", id);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelErrorDelete.Text = "Brisanje uspješno!";
            }
            else
            {
                this.labelErrorDelete.Text = "Pogreška kod brisanja komentara Korisnika!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    public static void deleteDirectory(string path)
    {
        foreach (string directory in Directory.GetDirectories(path))
        {
            deleteDirectory(directory);
        }

        try
        {
            Directory.Delete(path, true);
        }
        catch (IOException)
        {
            Directory.Delete(path, true);
        }
        catch (UnauthorizedAccessException)
        {
            Directory.Delete(path, true);
        }
    }

    private String LimitString(String data, int length)
    {
        return (data == null || data.Length < length)
          ? data
          : data.Substring(0, length);
    }

    private void deleteUserLikes(int userID)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("DELETE FROM likes WHERE whoLikes=@id", konekcija);
            upit.Parameters.AddWithValue("@id", userID);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelErrorDelete.Text = "Brisanje uspješno!";
            }
            else
            {
                //this.errLike.Text = "Pogreška kod brisanja Galerije!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

}