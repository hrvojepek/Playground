using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Data.OleDb;
using System.Configuration;
using System.Data;
using System.IO;

public partial class GallerySingle : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

        this.userName.Text = (string)Session["userFName"];
        this.userNameFooter.Text = (string)Session["userFName"];

        int galleryID = Convert.ToInt16(Request.QueryString["ID"]);
        String galleryCategory = null;
        int userID = 0;
        bool isPublic = false;
        String photos = null;
        String galleryPath = null;
        String ownerEmail = null;

        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            string query = "Select * from galleries where ID=" + galleryID;
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();


            while (podatci.Read())
            {
                userID = Convert.ToInt16(podatci["userID"].ToString());
                galleryCategory = podatci["gallCategory"].ToString();
                isPublic = (bool)podatci["public"];
                photos = podatci["photos"].ToString();
                galleryPath = podatci["gallPath"].ToString();

                this.galleryName.Text = podatci["gallName"].ToString();
                this.nameGall.Text = podatci["gallName"].ToString();
                this.gallDesc.Text = podatci["gallDescription"].ToString();
                this.gallDate.Text = podatci["publishDate"].ToString();
                this.numOfLikes.Text = podatci["numOfLikes"].ToString();
                this.gallCategoryLbl.Text = galleryCategory;

            }
            podatci.Close();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }

        //get gallery owner all informations
        try
        {
            string query = "Select * from users where ID=" + userID;
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();


            while (podatci.Read())
            {
                this.ownerName.Text = podatci["firstName"].ToString() + " " + podatci["lastName"].ToString();
                this.ownerEmail.Text = podatci["email"].ToString();
                ownerEmail = podatci["email"].ToString();
                this.ownerSex.Text = podatci["sex"].ToString();
                this.ownerQuote.Text = podatci["greetingMessage"].ToString();
                this.numOfGalleries.Text = podatci["numOfGalleries"].ToString();

                HyperLink link1 = new HyperLink();
                link1.Text = "Pogledaj Profil";
                link1.CssClass = "hvr-sweep-to-right more";
                link1.NavigateUrl = "ProfileUser.aspx?ID=" + podatci["ID"];
                this.galleryOwnerProfile.Controls.Add(link1);
            }
            podatci.Close();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }

        //Put all photos in slider
        List<String> allGalleryPhotos = photos.Split(';').ToList();
        LiteralControl slider = new LiteralControl();

        foreach (String p in allGalleryPhotos)
        {
            String photoPath = "users/" + ownerEmail + "/Galleries/" + galleryID + "/" + p;

            String thumbBefore = @"<li data-thumb='";
            slider.Text += thumbBefore;
            String addThumb = photoPath + @"'>";
            slider.Text += addThumb;

            String imageBefore = @"<img src='";
            slider.Text += imageBefore;
            String addImage = photoPath + @"' /></li>";
            slider.Text += addImage;

        }

        this.PhotosOfGallery.Controls.Add(slider);

        int loggedUser = Convert.ToInt16(Session["userID"]);
        if (User.Identity.IsAuthenticated == false)
        {
            this.BttnDeleteGallery.Visible = false;
            this.BttnLike.Visible = false;
        }
        //if the user who is logged is the owner of gallery he can delete it and cannot like it
        else if (userID == loggedUser)
        {
            this.BttnDeleteGallery.Visible = true;
            this.BttnLike.Visible = false;
        }

        else
        {
            this.BttnDeleteGallery.Visible = false;
            this.BttnLike.Visible = true;

            //if the user who is logged in had already liked gallery disable button like
            if (didUserLikedGallery(loggedUser, galleryID))
            {
                this.BttnLike.Enabled = false;
                this.errLike.Text = "Već ste lajkali ovu galeriju";
            }
        }

        //getGalleries in same category on right
        getGalleriesInSameCategory(galleryCategory);

    }

    protected void BttnLogout_Click(object sender, EventArgs e)
    {
        FormsAuthentication.SignOut();
        FormsAuthentication.RedirectToLoginPage();
        Session.RemoveAll();
        Session.Abandon();
    }

    protected void BttnLike_Click(object sender, EventArgs e)
    {
        int galleryID = Convert.ToInt16(Request.QueryString["ID"]);
        int userID = Convert.ToInt16(Session["userID"].ToString());

        like(userID, galleryID);

        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("UPDATE galleries SET numOfLikes = numOfLikes + 1 WHERE ID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", galleryID);
            int broj = upit.ExecuteNonQuery();

        }
        catch { }
        finally
        {
            konekcija.Close();
        }

        Response.Redirect("~/GallerySingle.aspx?ID=" + galleryID.ToString());
    }

    protected void BttnDeleteGallery_Click(object sender, EventArgs e)
    {
        int galleryID = Convert.ToInt16(Request.QueryString["ID"]);
        String userMail = (string)Session["email"].ToString();
        int userID = Convert.ToInt16(Session["userID"].ToString());

        string path = Server.MapPath("~/users/" + userMail + "/Galleries/" + galleryID);
        decrementUserNumOfGalleries(userID);
        deleteDirectory(path);
        deleteGalleryLikes(galleryID);
        deleteGallery(galleryID);
        Response.Redirect("~/ProfileUser.aspx");
    }

    private Boolean didUserLikedGallery(int userID, int galleryID)
    {
        DataTable dt = new DataTable();
        string query = "SELECT * FROM likes WHERE whoLikes=" + userID + "AND whatIsLiked=" + galleryID;
        dt = ConnectToDatabase.GetData(query);
        if (dt.Rows.Count > 0 && dt != null)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private void like(int userID, int galleryID)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("INSERT INTO likes VALUES(@whoLikes, @whatIsLiked)", konekcija);
            upit.Parameters.AddWithValue("@whoLikes", userID);
            upit.Parameters.AddWithValue("@whatIsLiked", galleryID);
            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.errLike.Text = "";
            }
            else
            {
                this.errLike.ForeColor = System.Drawing.Color.Red;
                this.errLike.Text = "Pogreška kod lajkanja galerije!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private void deleteGallery(int galleryID)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("DELETE FROM galleries WHERE ID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", galleryID);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelErrorDelete.Text = "Brisanje uspješno!";
            }
            else
            {
                this.errLike.Text = "Pogreška kod brisanja Galerije!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private void deleteDirectory(string path)
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

    private void deleteGalleryLikes(int galleryID)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("DELETE FROM likes WHERE whatIsLiked=@id", konekcija);
            upit.Parameters.AddWithValue("@id", galleryID);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelErrorDelete.Text = "Brisanje uspješno!";
            }
            else
            {
                this.errLike.Text = "Pogreška kod brisanja Galerije!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private void decrementUserNumOfGalleries(int userID)
    {
        Session["numberOfGalleries"] = Convert.ToInt16(Session["numberOfGalleries"]) - 1;
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("UPDATE users SET numOfGalleries = numOfGalleries - 1 WHERE ID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", userID);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                //this.labelError.ForeColor = System.Drawing.Color.Green;
                //this.labelError.Text = "Uspješno!";
            }
            else
            {
                //this.labelError.Text = "Pogreška!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }

    }

    private void getGalleriesInSameCategory(String galleryCategory)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            string query = null;
            if (User.Identity.IsAuthenticated == true)
            {
                query = "Select * from galleries where gallCategory='" + galleryCategory + "'";
            }
            else
            {
                query = "Select * from galleries where public=true and gallCategory='" + galleryCategory + "'";
            }

            konekcija.Open();
            OleDbCommand upit = new OleDbCommand(query, konekcija);
            OleDbDataReader podatci = upit.ExecuteReader();

            int i = 0;
            while (podatci.Read() && i<7)
            {
                LiteralControl beforeBttn = new LiteralControl();
                LiteralControl afterBttn = new LiteralControl();

                String firstPhotoPath = podatci["firstPhotoPath"].ToString();
                String gallName = podatci["gallName"].ToString();
                String gallID = podatci["ID"].ToString();

                String start = @"<div class='single-box-img'>
                            <div class='box-img'>
                                <img class='img-responsive' src='";
                beforeBttn.Text += start;

                String addPhoto = firstPhotoPath + @"'>
                            </div>
                            <div class='box-text'>
                                <p>";
                beforeBttn.Text += addPhoto;

                String addGallName = gallName + @"</p>";

                beforeBttn.Text += addGallName;

                this.galleriesCategory.Controls.Add(beforeBttn);

                HyperLink link1 = new HyperLink();
                link1.Text = "Pogledaj";
                link1.CssClass = "in-box";
                link1.NavigateUrl = "GallerySingle.aspx?ID=" + gallID;
                this.galleriesCategory.Controls.Add(link1);


                String end = @"</div><div class='clearfix'></div></div>";
                afterBttn.Text += end;
                this.galleriesCategory.Controls.Add(afterBttn);

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

}