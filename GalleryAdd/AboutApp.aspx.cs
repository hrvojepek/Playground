using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;

public partial class AboutApp : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
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

}