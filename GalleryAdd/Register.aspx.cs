using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Data;
using System.IO;
using System.Drawing;
using System.Data.OleDb;
using System.Configuration;

public partial class Register : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (User.Identity.IsAuthenticated == true)
        {
            Response.Redirect("~/ProfileUser.aspx");
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


    protected void Register_Click(object sender, EventArgs e)
    {
        String fName = this.fNameTxtBox.Text;
        String lName = this.lNameTxtBox.Text;
        String email = this.emailTxtBox.Text;
        String msg = this.messageTxtBox.Text;
        String sex = this.sex.SelectedValue;
        String profilePicturePath = null;
        String password = this.passwordTxtBox.Text;

        int numOfGall = 0;
        int avgGrade = 0;

        int id = lastUserID();
        id = id + 1;

        //ime slike
        string FileName = Path.GetFileName(profilePicture.PostedFile.FileName);

        //Check if there is registered user with input email
        if (isThereUser(email) == true)
        {
            this.labelError.Text = "Korisnički račun s unesenim mailom već postoji!";
        }

        else
        {
            Directory.CreateDirectory(Server.MapPath("~/users/" + email));
            Directory.CreateDirectory(Server.MapPath("~/users/" + email + "/profilePhoto/"));
            Directory.CreateDirectory(Server.MapPath("~/users/" + email + "/Galleries/"));

            if (FileName.Equals(null) || FileName.Equals(""))
            {
                File.Copy(Server.MapPath("~/images/noPhoto.jpg"), Server.MapPath("~/users/" + email + "/profilePhoto/noPhoto.jpg"));
                profilePicturePath = "users/" + email + "/profilePhoto/noPhoto.jpg";
            }
            else
            {
                //Save picture to server but without resizing
                profilePicture.SaveAs(Server.MapPath("~/users/" + email + "/profilePhoto/" + FileName));
                profilePicturePath = "users/" + email + "/profilePhoto/" + FileName;

                //resizing profile picture --- I had some error and solve problem of big photos on another way
                /*HttpPostedFile pf = profilePicture.PostedFile;
                System.Drawing.Image bm = System.Drawing.Image.FromStream(pf.InputStream);
                bm = ResizeBitmap((Bitmap)bm, 250, 250); /// new width, height
                bm.Save(Path.Combine("~/users/" + email + "/profilePhoto/", pf.FileName));
                profilePicturePath = "users/" + email + "/profilePhoto/" + FileName;*/
            }

            registerUser(id, fName, lName, email, msg, sex, profilePicturePath, password, numOfGall, avgGrade);

            if (this.CheckBoxLoginNow.Checked == true)
            {
                InstantLogin(email, password);
            }
        }

    }

    private void InstantLogin(String email, String password)
    {
        DataTable dt = new DataTable();
        string query = "Select * from users where email='" + email + "' and password='" + password + "'";
        dt = ConnectToDatabase.GetData(query);
        if (dt.Rows.Count > 0 && dt != null)
        {
            Session["userID"] = dt.Rows[0]["ID"]; // user id
            Session["userFName"] = dt.Rows[0]["firstName"]; //first name
            Session["userLName"] = dt.Rows[0]["lastName"]; //last name
            Session["email"] = dt.Rows[0]["email"]; //email
            Session["greetingMessage"] = dt.Rows[0]["greetingMessage"]; //message under picture on profile page
            Session["profPicPath"] = dt.Rows[0]["profilePhoto"]; //profile picture
            Session["sex"] = dt.Rows[0]["sex"]; //male or female
            Session["numberOfGalleries"] = dt.Rows[0]["numOfGalleries"]; //numOfGalleries
            Session["avgGrade"] = dt.Rows[0]["averageGrade"]; //avg of grades of galleries

            FormsAuthentication.RedirectFromLoginPage(dt.Rows[0]["firstName"].ToString(), false);
        }
    }

    private Bitmap ResizeBitmap(Bitmap b, int nWidth, int nHeight)
    {
        Bitmap result = new Bitmap(nWidth, nHeight);
        using (Graphics g = Graphics.FromImage((System.Drawing.Image)result))
            g.DrawImage(b, 0, 0, nWidth, nHeight);
        return result;
    }

    private Boolean isThereUser(String emejl)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        
        DataTable dt = new DataTable();
        string query = "Select * from users where email='" + emejl + "'";
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

    private void registerUser(int id, String fName, String lName, String email, String msg, String sex, String profPhotoPath, String password, int numOfGall, int avgGr)
    {
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("INSERT INTO users VALUES (@id, @firstName, @lastName, @email, @password, @profPhoto, @greeMsg, @sex, @numGalleries, @avgGrade)", konekcija);
            upit.Parameters.AddWithValue("@id", id);
            upit.Parameters.AddWithValue("@firstName", fName);
            upit.Parameters.AddWithValue("@lastName", lName);
            upit.Parameters.AddWithValue("@email", email);
            upit.Parameters.AddWithValue("@password", password);
            upit.Parameters.AddWithValue("@profPhoto", profPhotoPath);
            upit.Parameters.AddWithValue("@greeMsg", msg);
            upit.Parameters.AddWithValue("@sex", sex);
            upit.Parameters.AddWithValue("@numGalleries", numOfGall);
            upit.Parameters.AddWithValue("@avgGrade", avgGr);

            int broj = upit.ExecuteNonQuery();

            if (broj > 0)
            {
                this.labelError.ForeColor = System.Drawing.Color.Green;
                this.labelError.Text = "Uspješno ste se registrirali!";
            }
            else
            {
                this.labelError.Text = "Pogreška kod registracija!";
            }
        }
        catch { }
        finally
        {
            konekcija.Close();
        }
    }

    private int lastUserID()
    {
        int lastID = 0;

        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
            konekcija.Open();
            OleDbCommand upit = new OleDbCommand("Select MAX(ID) FROM users", konekcija);
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

}