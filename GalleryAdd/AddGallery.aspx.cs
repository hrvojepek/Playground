using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.IO;
using System.Drawing;
using System.Data.OleDb;
using System.Configuration;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Imaging; 

public partial class AddGallery : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (User.Identity.IsAuthenticated == false)
        {
            Response.Redirect("~/Index.aspx");
        }

        this.userName.Text = (string)Session["userFName"];
        this.userNameFooter.Text = (string)Session["userFName"];
    }

    protected void BttnLogout_Click(object sender, EventArgs e)
    {
        FormsAuthentication.SignOut();
        FormsAuthentication.RedirectToLoginPage();
        Session.RemoveAll();
        Session.Abandon();
    }

    protected void bttnAddGallery_Click(object sender, EventArgs e)
    {
        String gallName = this.nazivGalTxtBox.Text;
        String gallDescr = this.opisGalTxtBox.Text;
        bool pub = this.publicCheckBox.Checked;
        String gallCateg = this.DropDownListCategory.SelectedValue;
        int userID = Convert.ToInt32(Session["userID"]);
        String emailUser = (string)Session["email"];
        int idGallery = lastGalleryID();
        idGallery = idGallery + 1;
        int numOfLikes = 0;
        String photos = null;
        String dateAdded = DateTime.Now.ToString("dd/MM/yyyy");
        String firstPhoto = null;

        String filepath = "users/" + emailUser + "/Galleries/" + idGallery + "/";        
        incrementUserNumOfGalleries(userID);

        List<String> allPhotos = new List<String>();
        Directory.CreateDirectory(Server.MapPath("~/users/" + emailUser + "/Galleries/" + idGallery));



        foreach (HttpPostedFile postedFile in galleryPhotos.PostedFiles)
        {
            String fileName = Path.GetFileName(postedFile.FileName);
            allPhotos.Add(fileName);
            postedFile.SaveAs(Server.MapPath("~/users/" + emailUser + "/Galleries/" + idGallery + "/") + fileName);
            
            /*resizing
            // Create a bitmap of the content of the fileUpload control in memory
            Bitmap originalBMP = new Bitmap(galleryPhotos.FileContent);
            
            // Calculate the new image dimensions
            int newWidth = 900;
            int newHeight = 420;

            // Create a new bitmap which will hold the previous resized bitmap
            Bitmap newBMP = new Bitmap(originalBMP, newWidth, newHeight);
            // Create a graphic based on the new bitmap
            Graphics oGraphics = Graphics.FromImage(newBMP);

            // Set the properties for the new graphic file
            oGraphics.SmoothingMode = SmoothingMode.AntiAlias; oGraphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
            // Draw the new graphic based on the resized bitmap
            oGraphics.DrawImage(originalBMP, 0, 0, newWidth, newHeight);

            // Save the new graphic file to the server
            newBMP.Save(Server.MapPath("~/users/" + emailUser + "/Galleries/" + idGallery + "/") + fileName);

            // Once finished with the bitmap objects, we deallocate them.
            originalBMP.Dispose();
            newBMP.Dispose();
            oGraphics.Dispose();*/
        }

        firstPhoto = allPhotos.ElementAt(0);
        String firstPhotoPath = "users/" + emailUser + "/Galleries/" + idGallery + "/" + firstPhoto;
        photos = String.Join(";", allPhotos.ToArray());
        
        insertGallery(idGallery, gallName, gallDescr, pub, numOfLikes, gallCateg, dateAdded, photos, userID, filepath, firstPhotoPath);
        Response.Redirect("~/ProfileUser.aspx");
    }

    private void insertGallery(int id, String nameGall, String descrGall, bool publ, int numOfLikes, String categoryGall, String publDate, String photos, int userID, String pathOfGall, String firstPhotoPath)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("INSERT INTO galleries VALUES (@id, @gallName, @gallDescription, @pub, @numLikes, @gallCategory, @publishDate, @photosG, @usrID, @pathGall, @pathPhoto)", konekcija);
            upit.Parameters.AddWithValue("@id", id);
            upit.Parameters.AddWithValue("@gallName", nameGall);
            upit.Parameters.AddWithValue("@gallDescription", descrGall);
            upit.Parameters.AddWithValue("@pub", publ);
            upit.Parameters.AddWithValue("@numLikes", numOfLikes);
            upit.Parameters.AddWithValue("@gallCategory", categoryGall);
            upit.Parameters.AddWithValue("@publishDate", publDate);
            upit.Parameters.AddWithValue("@photosG", photos);
            upit.Parameters.AddWithValue("@usrID", userID);
            upit.Parameters.AddWithValue("@pathGall", pathOfGall);
            upit.Parameters.AddWithValue("@pathPhoto", firstPhotoPath);

            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                this.labelError.ForeColor = System.Drawing.Color.Green;
                this.labelError.Text = "Uspješno ste dodali Galeriju!";
            }
            else
            {
                this.labelError.Text = "Pogreška kod dodavanja Galerije!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private int lastGalleryID()
    {
        int lastID = 0;

        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("Select MAX(ID) FROM galleries", konekcija);
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

    private void incrementUserNumOfGalleries(int userID)
    {
        Session["numberOfGalleries"] = Convert.ToInt16(Session["numberOfGalleries"]) + 1;
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("UPDATE users SET numOfGalleries = numOfGalleries + 1 WHERE ID=@id", konekcija);
            upit.Parameters.AddWithValue("@id", userID);


            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                this.labelError.ForeColor = System.Drawing.Color.Green;
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


}
