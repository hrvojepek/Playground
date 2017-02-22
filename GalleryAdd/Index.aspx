<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Index.aspx.cs" Inherits="Index" %>

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

    <!-- slide -->
    <script src="js/responsiveslides.min.js"></script>
    <script>
        $(function () {
            $("#slider").responsiveSlides({
                auto: true,
                speed: 500,
                namespace: "callbacks",
                pager: true,
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
                            <li><span><a href="ProfileUser.aspx">
                                <asp:Label ID="userName" runat="server" Text=""></asp:Label></a>
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

            <div class=" header-right">
                <div class=" banner">
                    <div class="slider">
                        <div class="callbacks_container">
                            <ul class="rslides" id="slider">
                                <li>
                                    <div class="banner1">
                                        <div class="caption">
                                            <h3><span>Napravite svoju galeriju</span></h3>
                                            <p>...nikada brže i jednostavnije...</p>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="banner2">
                                        <div class="caption">
                                            <h3><span>Dijelite slike</span></h3>
                                            <p>...s prijateljima i ostatkom svijeta...</p>
                                        </div>
                                    </div>
                                </li>
                                <li>
                                    <div class="banner3">
                                        <div class="caption">
                                            <h3><span>Registrirajte se odmah</span></h3>
                                            <p>...i uživajte u aplikaciji...</p>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <!--header-bottom-->
            <div class="banner-bottom-top">
                <div class="container">
                    <div class="bottom-header">
                        <div class="header-bottom">
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active"><a href="Index.aspx">Home</a></li>
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


            <!--//header-->

            <!--content-->
            <div class="content">

                <!--service-->
                <div class="services">
                    <div class="container">
                        <div class="service-top">
                            <a href="login.html">
                                <h3>Registriraj se</h3>
                            </a>
                            <p>...i postani dio naše obitelji...</p>
                        </div>
                        <div class="services-grid">
                            <div class="col-md-6 service-top1">
                                <div class=" ser-grid">
                                    <a href="#" class="hi-icon hi-icon-archive glyphicon glyphicon-user"></a>
                                </div>
                                <div class="ser-top">
                                    <h4>Postani naš korisnik</h4>
                                    <p>Ukoliko se još niste registrirali učinite to sada.</p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="col-md-6 service-top1">
                                <div class=" ser-grid">
                                    <a href="#" class="hi-icon hi-icon-archive glyphicon glyphicon-globe"></a>
                                </div>
                                <div class="ser-top">
                                    <h4>Dostupni smo - UVIJEK</h4>
                                    <p>
                                        Sve što trebate je veza na Internet i u svakom trenutku uživajte u svojim, a i galerijama
                                        ostalih korisnika.
                                    </p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="services-grid">
                            <div class="col-md-6 service-top1">
                                <div class=" ser-grid">
                                    <a href="#" class="hi-icon hi-icon-archive glyphicon glyphicon-cog"></a>
                                </div>
                                <div class="ser-top">
                                    <h4>O čemu se ovdje radi?</h4>
                                    <p>
                                        Aplikacija je osmišljena kako bi korisnici mogli što lakše dijeliti galerije slika
                                        sa svojim bližnjima ili ostatkom svijeta.
                                    </p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="col-md-6 service-top1">
                                <div class=" ser-grid">
                                    <a href="#" class="hi-icon hi-icon-archive glyphicon glyphicon-eye-open"></a>
                                </div>
                                <div class="ser-top">
                                    <h4>Galerije ne moraju biti vidljive svima</h4>
                                    <p>Postavite svoju galeriju na private i bit će vidljiva samo registriranim korisnicima.</p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div>
                <!--//services-->

                <!--project--->
                <div class="project">
                    <div class="container">
                        <h3>Popularne Galerije</h3>
                        <div class="dealer-top">
                            <div class="deal-top-top">

                                <asp:PlaceHolder ID="topGalleriesPlaceHolder" runat="server">

                                </asp:PlaceHolder>

                                <div class="clearfix"></div>
                            </div>

                        </div>
                    </div>
                </div>
                <!--//project-->

                <!--test-->
                <div class="content-bottom">
                    <div class="container">
                        <h3>Komentari Nekih od Korisnika</h3>
                        <div class="col-md-6 name-in">

                            <asp:PlaceHolder ID="CommentsFaS" runat="server">

                            </asp:PlaceHolder>
                        </div>


                        <div class="col-md-6  name-on">
                            <asp:PlaceHolder ID="CommentsFaT" runat="server">

                            </asp:PlaceHolder>
                        </div>

                        <div class="clearfix"></div>
                    </div>

                    <div style="float: right; margin-right: 15%;">
                        <% if (User.Identity.IsAuthenticated == true)
                           { %>
                        <asp:Button ID="Button1" CssClass="btn btn-primary" runat="server" Text="Komentiraj i TI!" OnClientClick="ToggleDiv();return false;" />
                        <% } %>
                        <% else
                           { %>
                        <p style="color: white; font-size: 1em;">
                            <a style="color: white; font-size: 1em; font-weight: bold;" href="Login.aspx">Ulogirajte se</a> da bi komentirali!
                        </p>
                        <% } %>
                    </div>

                </div>

                <div id="commentDiv" style="display: none;">
                    <br />
                    <br />
                    <div>
                        <label style="color: white;">Komentar upiši ovdje:</label><br />
                        <br />
                        <asp:TextBox ID="commentTxtBox" runat="server" CssClass="form-control"></asp:TextBox>
                    </div>
                    <br />
                    <div style="float: right;">
                        <button type="button" class="btn btn-primary btn-sm" onclick="HideDiv();">
                            <span class="glyphicon glyphicon-chevron-up"></span>Sakrij
                        </button>
                        <asp:Button ID="BttnCommentPage" runat="server" CssClass="btn btn-primary" Text="Komentiraj" OnClick="BttnCommentPage_Click" />
                    </div>
                    <br />
                    <asp:Label ID="errLabelComm" runat="server" Text="" CssClass="errorLogin"></asp:Label>
                    <br />
                </div>

            </div>
            <!--//test-->
            <!--partners-->
            <div class="content-bottom1">
                <h3>Korišteni izvori</h3>
                <div class="container">
                    <ul>
                        <li><a href="#">
                            <img class="img-responsive" src="images/tvzLogo.png" alt="" /></a></li>
                        <li><a href="#">
                            <img class="img-responsive" src="images/micLogo.png" alt="" /></a></li>
                        <li><a href="#">
                            <img class="img-responsive" src="images/w3logo.png" alt="" /></a></li>
                        <li><a href="#">
                            <img class="img-responsive" src="images/bootstrap.png" alt="" /></a></li>
                        <li><a href="#">
                            <img class="img-responsive" src="images/aspLogo.png" alt="" /></a></li>
                        <li><a href="#">
                            <img class="img-responsive" src="images/SOlogo.png" alt="" /></a></li>

                    </ul>

                </div>
            </div>
            <!--//partners-->
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
