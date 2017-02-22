using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;
using System.Data.OleDb;
using System.Configuration;

public partial class Galleries : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (User.Identity.IsAuthenticated == true)
        {
            this.userName.Text = (string)Session["userFName"];
            this.userNameFooter.Text = (string)Session["userFName"];
        }
        else
        {
            this.publicLabel.Visible = true;
        }

    }

    protected void BttnLogout_Click(object sender, EventArgs e)
    {
        FormsAuthentication.SignOut();
        FormsAuthentication.RedirectToLoginPage();
        Session.RemoveAll();
        Session.Abandon();
    }

    protected void CategoryDropDown_SelectedIndexChanged(object sender, EventArgs e)
    {
        String query = null;
        this.categoryLabel.Text = CategoryDropDown.SelectedValue;
        if (User.Identity.IsAuthenticated == true)
        {
            query = "SELECT * FROM galleries where gallCategory='" + CategoryDropDown.SelectedValue + "'";
        }
        else
        {
            query = "SELECT * FROM galleries where public=true and gallCategory='" + CategoryDropDown.SelectedValue + "'";
        }

        bool hasGalleries = getGalleries(query);

        if (!hasGalleries)
        {
            this.noGalleries.Visible = true;
        }
        else
        {
            this.noGalleries.Visible = false;
        }
    }

    private bool getGalleries(String query)
    {
        bool hasGalleries = false;
        OleDbConnection konekcija = new OleDbConnection(ConfigurationManager.ConnectionStrings["konekcijaNaBazu"].ConnectionString);
        try
        {
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
                
                LiteralControl beforeImage = new LiteralControl();
                String divStart = @"<div style='margin-bottom: 2%; float:left;' class='col-md-3 top-deal-top'>
                            <div class='top-deal'>
                                <a href='#' class='mask'>";
                beforeImage.Text += divStart;
                this.AllGalleries.Controls.Add(beforeImage);


                Image im = new Image();
                im.ImageUrl = firstPhotoPath;
                im.CssClass = "img-responsive zoom-img";
                im.Width = 300;
                im.Height = 200;
                this.AllGalleries.Controls.Add(im);


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

                this.AllGalleries.Controls.Add(afterImage);

                HyperLink link1 = new HyperLink();
                link1.Text = "Pogledaj";
                link1.CssClass = "hvr-sweep-to-right more";
                link1.NavigateUrl = "GallerySingle.aspx?ID=" + podatci["ID"];
                this.AllGalleries.Controls.Add(link1);


                LiteralControl afterLink = new LiteralControl();
                String divEnd = @"</div><div class='clearfix'></div> </div></div></div>";
                afterLink.Text += divEnd;
                this.AllGalleries.Controls.Add(afterLink);

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

    private String LimitString(String data, int length)
    {
        return (data == null || data.Length < length)
          ? data
          : data.Substring(0, length);
    }


}