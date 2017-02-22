<%@ Page Language="C#" AutoEventWireup="true" CodeFile="ProfileUser.aspx.cs" Inherits="ProfileUser" %>

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
                                <asp:Label ID="userName" runat="server" Text=""></asp:Label>
                            </span></li>
                            <asp:Button ID="BttnLogout" CssClass="btn btn-primary" runat="server" OnClick="BttnLogout_Click" Text="Logout" />
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
                    <h3><span>PODACI O KORISNIKU</span></h3>
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
                                <% if (Request.QueryString == null && Request.QueryString.Count == 0)
                                   { %>
                                <li role="presentation" class="active"><a href="ProfileUser.aspx">Profil</a></li>
                                <% }
                                   else
                                   { %>
                                <li role="presentation" class=""><a href="ProfileUser.aspx">Profil</a></li>
                                <% } %>
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

        <!--PROFIL KORISNIKA-->
        <div class="dealers">
            <div class="container">
                <h3>
                    <asp:Label ID="nameAndSurnameLbl" runat="server" Text=""></asp:Label>
                </h3>
                <div class="dealer">
                    <h4>Podaci o korisniku:</h4>
                    <div class="dealer-grid">
                        <div class="col-sm-4 dealer-grid1">
                            <div class="dealer-grid-top">
                                <!-- SLIKA PROFILA -->
                                <asp:PlaceHolder ID="ProfilePicturePHolder" runat="server"></asp:PlaceHolder>
                                <img src="" style="border-radius: 10px; max-width: 250px; max-height: 250px; min-height: 200px; min-width: 200px;" />
                                <div class="clearfix"></div>
                            </div>
                            <p>
                                <asp:Label ID="greetMsgLbl" runat="server" Text=""></asp:Label>
                            </p>
                            <br />
                            <br />
                            <br />
                            <asp:Button ID="BttnDeactivate" CssClass="btn btn-danger btn-lg btn-block" runat="server" Text="Deaktiviraj svoj račun" OnClick="BttnDeactivate_Click" OnClientClick="return confirm('Jeste sigurni da želite deaktivirati svoj korisnički račun?')" />
                            <asp:Label ID="labelErrorDelete" CssClass="errorLogin" runat="server" Text=""></asp:Label>
                            <br />
                            <asp:Button ID="BttnAddGallery" runat="server" CssClass="btn btn-success btn-lg btn-block" Text="+ Dodaj Galeriju" OnClick="BttnAddGallery_Click" />
                        </div>
                        <div class="col-sm-4 dealer-grid1">
                            <div class="dealer-grid-top">


                                <div class="clearfix"></div>
                            </div>
                            <p><span style="font-weight: bold; color: black;">Spol:</span><asp:Label ID="userSexLbl" runat="server" Text=""></asp:Label></p>
                            <p><span style="font-weight: bold; color: black;">Email:</span>
                                <asp:Label ID="userEmailLbl" runat="server" Text=""></asp:Label>
                            </p>
                            <p><span style="font-weight: bold; color: black;">Broj objavljenih galerija:</span>
                                <asp:Label ID="numOfGalleriesLbl" runat="server" Text=""></asp:Label>
                            </p>
                        </div>
                        <div class="col-sm-4 dealer-grid1">
                            <div class="dealer-grid-top">

                                <div class="clearfix"></div>
                            </div>

                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>


                <div class="dealer-top">
                    <h4>Galerije Korisnika:</h4>
                    <div class="deal-top-top">

                        <asp:PlaceHolder ID="Gallery" runat="server"></asp:PlaceHolder>

                        <asp:Label ID="noGalleries" runat="server" Text="Nema još objavljenih galerija" Visible="false"></asp:Label>

                        <!--GallerySTART
                        <div style="margin-bottom: 2%;" class="col-md-3 top-deal-top">
                            <div class="top-deal">
                                <a href="galerijaSingle.html" class="mask">
                                    <img src="images/de.jpg" class="img-responsive zoom-img" alt=""></a>
                                <div class="deal-bottom">
                                    <div class="top-deal1">
                                        <h5><a href="galerijagalerijaSingle.html">IME GALERIJE</a></h5>
                                        <p></p>
                                    </div>
                                    <div class="top-deal2">
                                        <a href="galerijagalerijaSingle.html" class="hvr-sweep-to-right more">Pogledaj</a>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                        </div>
                        GalleryEND-->
                        <div class="clearfix"></div>
                    </div>
                </div>

            </div>
        </div>

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
