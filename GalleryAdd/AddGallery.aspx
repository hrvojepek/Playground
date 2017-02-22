<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AddGallery.aspx.cs" Inherits="AddGallery" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>GalleryADD</title>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Custom Theme files -->
    <!--menu-->
    <script src="js/scripts.js"></script>
    <link href="css/styles.css" rel="stylesheet" />
    <!--//menu-->
    <!--theme-style-->
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
    <!--//theme-style-->
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="" />

    <link rel="stylesheet" href="css/stylesM.css" />
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
    <script src="js/scriptM.js"></script>

    <script>
        $(document).ready(function () {
            $('#galleryPhotos').change(function () {
                var files = $(this)[0].files;
                if (files.length < 2) {
                    document.getElementById("validate-status").style.color = "red";
                    $("#validate-status").text("Galerija mora imati barem 2 fotografije!");
                    document.getElementById("bttnAddGallery").disabled = true;
                }
                else if (files.length>8){
                    document.getElementById("validate-status").style.color = "red";
                    $("#validate-status").text("Galerija ne smije imati više od 8 fotografija!");
                    document.getElementById("bttnAddGallery").disabled = true;
                }

                else {
                    document.getElementById("validate-status").style.color = "white";
                    $("#validate-status").text("");
                    document.getElementById("bttnAddGallery").disabled = false;
                }
            });
        });

    </script>

</head>
<body>
    <form id="form1" runat="server">

        <div class="header">
            <div class="container">
                <div class="cont2">
                    <!--logo-->
                    <div class="logo">
                        <h1><a href="Index.aspx">GalleryADD</a></h1>
                    </div>
                    <!--//logo-->
                    <div class="top-nav">
                        <ul class="right-icons">
                            <!---ASP LOGIN INFO---->
                            <li><span><i class="glyphicon glyphicon-user"></i></span></li>
                            <% if (User.Identity.IsAuthenticated == true)
                               { %>
                            <li><span>
                                <a href="ProfileUser.aspx"><asp:Label ID="userName" runat="server" Text=""></asp:Label></a>
                            </span></li>
                            <asp:Button ID="BttnLogout" CssClass="btn btn-primary" runat="server" OnClick="BttnLogout_Click" CausesValidation="false" Text="Logout" />
                            <% } %>

                            <%else
                               { %>
                            <li><a href="Login.aspx"><i class=""></i>Login</a></li>
                            <% } %>
                        </ul>
                        <div class="nav-icon">
                            <% if (User.Identity.IsAuthenticated == true)
                               { %>

                            <% } %>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                </div>

            </div>
            <!--//-->

            <div class=" banner-buying">
                <div class=" container">
                    <h3><span>DODAJ SVOJU GALERIJU</span></h3>
                </div>
            </div>

            <!--header-bottom-->
            <div class="banner-bottom-top">
                <div class="container">
                    <div class="bottom-header">
                        <div class="header-bottom">
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation"><a href="Index.aspx">Home</a></li>
                                <li role="presentation"><a href="AboutApp.aspx">O Aplikaciji</a></li>
                                <li role="presentation"><a href="Galleries.aspx">Galerije</a></li>
                                <% if (User.Identity.IsAuthenticated == true)
                                   { %>
                                <li role="presentation"><a href="ProfileUser.aspx">Profil</a></li>
                                <% } %>
                            </ul>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
            </div>
            <!--//-->
            <!--//header-bottom-->
        </div>
        <!--//header-->


        <!--register-->
        <div class="login-right">
            <div class="container">
                <h3>Podaci o Galeriji</h3>
                <div class="login-top">

                    <div class="">

                        <label>Naziv Galerije:</label>
                        <asp:TextBox runat="server" ID="nazivGalTxtBox" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidatornaziv" runat="server" CssClass="errorLogin" ErrorMessage="Morate unijeti naziv Galerije!" ControlToValidate="nazivGalTxtBox"></asp:RequiredFieldValidator>
                        <br />

                        <label>Opis Galerije:</label>
                        <asp:TextBox runat="server" ID="opisGalTxtBox" TextMode="multiline" Columns="50" Rows="5" CssClass="form-control" />
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" CssClass="errorLogin" ErrorMessage="Morate unijeti opis Galerije!" ControlToValidate="opisGalTxtBox"></asp:RequiredFieldValidator>
                        <br />

                        <asp:CheckBox ID="publicCheckBox" runat="server" />
                        <label>Public (označite ako želite da ju vide i neregistrirane osobe)</label>
                        <br />
                        <br />

                        <asp:DropDownList ID="DropDownListCategory" CssClass="form-control" runat="server" >
                            <asp:ListItem Text="Odaberite kategoriju" Value=""></asp:ListItem>
                            <asp:ListItem Text="Ljudi" Value="Ljudi"></asp:ListItem>
                            <asp:ListItem Text="Pejzaž" Value="Pejzaz"></asp:ListItem>
                            <asp:ListItem Text="Životinje" Value="Životinje"></asp:ListItem>
                            <asp:ListItem Text="Priroda" Value="Priroda"></asp:ListItem>
                            <asp:ListItem Text="Zgrade" Value="Zgrade"></asp:ListItem>
                            <asp:ListItem Text="Ostalo" Value="Ostalo"></asp:ListItem>
                        </asp:DropDownList>
                        <asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" CssClass="errorLogin" ErrorMessage="Morate odabrati kategoriju Galerije!" ControlToValidate="DropDownListCategory"></asp:RequiredFieldValidator>
                        <br />

                        <div class="form-group">
                            <label>Odaberite Slike:</label>
                            <asp:FileUpload ID="galleryPhotos" AllowMultiple="true" runat="server" accept=".png,.jpg,.jpeg," />
                            <p class="errorLogin" id="validate-status"><asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" CssClass="errorLogin" ErrorMessage="Morate uploadati slike Galerije!" ControlToValidate="galleryPhotos"></asp:RequiredFieldValidator></p>
                            <br />
                        </div>

                        <asp:Button runat="server" ID="bttnAddGallery" CssClass="btn btn-primary" OnClick="bttnAddGallery_Click" Text="Dodaj Galeriju" Width="117px" />

                        <br />
                        <asp:Label ID="labelError" CssClass="errorLogin" runat="server" Text=""></asp:Label>

                    </div>

                </div>
            </div>
        </div>
        <!--//register-->


        <!--footer-->
        <div class="footer">
            <div class="container">
                <div class="footer-top-at">
                    <div class="col-md-3 amet-sed">
                        <h4>GalleryADD</h4>
                        <ul class="nav-bottom">
                            <li><a href="AboutApp.aspx">O Aplikaciji</a></li>

                        </ul>
                    </div>
                    <div class="col-md-3 amet-sed ">
                        <h4>Menu</h4>
                        <ul class="nav-bottom">
                            <li><a href="Index.aspx">Početna</a></li>
                            <li><a href="Galleries.aspx">Galerije</a></li>
                            <% if (User.Identity.IsAuthenticated == true)
                               { %>
                            <li><a href="ProfileUser.aspx">Profil</a></li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="col-md-3 amet-sed">
                        <h4>Korisnička podrška</h4>

                        <ul class="nav-bottom">
                            <li><a href="Faqs.aspx">FAQ</a></li>
                            <% if (User.Identity.IsAuthenticated == true)
                               { %>
                            <li><a href="MakeSugestion.aspx">Dodaj prijedlog</a></li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="col-md-3 amet-sed ">
                        <h4>Login/Registracija</h4>
                        <% if (User.Identity.IsAuthenticated == true)
                           { %>
                        <p>
                            Ulogirani ste kao <span style="font-weight: bold;">
                                <asp:Label ID="userNameFooter" runat="server" Text=""></asp:Label></span>
                        </p>
                        <br />
                        <asp:Button ID="BttnLogout2" CssClass="btn btn-default" runat="server" OnClick="BttnLogout_Click" Text="Logout" />
                        <% } %>
                        <% else
                           { %>
                        <ul class="nav-bottom">
                            <li><a href="Login.aspx">Login</a></li>
                            <li><a href="Register.aspx">Registriraj se</a></li>
                        </ul>
                        <% } %>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="footer-bottom">
                <div class="container">
                    <div class="col-md-4 footer-logo">
                        <h2><a href="Index.aspx">GalleryADD</a></h2>
                    </div>
                    <div class="col-md-8 footer-class">
                        <p>© 2015 GalleryADD. All Rights Reserved | Design by Hrvoje Pek </p>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
        <!--//footer-->

    </form>
</body>
</html>
