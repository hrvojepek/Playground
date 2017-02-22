using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Web.Security;

public partial class Login : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (User.Identity.IsAuthenticated == true) {
            Response.Redirect("~/Index.aspx");
        }
    }

    protected void Login_Click(object sender, EventArgs e)
    {
        String email = this.emailTxtBox.Text;
        String pass = this.passwordTxtBox.Text;
        Boolean rememberME = this.CheckBoxRemembMe.Checked;
        //Boolean emailPassExists = false;

        DataTable dt = new DataTable();
        string query = "Select * from users where email='" + email + "' and password='" + pass + "'";
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

            FormsAuthentication.RedirectFromLoginPage(dt.Rows[0]["firstName"].ToString(), rememberME);

        }

        else
        {
            query = "Select * from users where email='" + email + "'";
            dt = ConnectToDatabase.GetData(query);
            if (dt.Rows.Count > 0 && dt != null)
            {
                this.labelError.Text = "Lozinka nije točna!";
            }
            else
            {
                this.labelError.Text = "Ne postoji korisnik s unesenim email-om! Registrirajte se!";
            }

        }
    }

    protected void BttnLogout_Click(object sender, EventArgs e)
    {
        FormsAuthentication.SignOut();
        FormsAuthentication.RedirectToLoginPage();
        Session.RemoveAll();
        Session.Abandon();

    }



}