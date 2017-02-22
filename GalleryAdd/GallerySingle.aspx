<%@ Page Language="C#" AutoEventWireup="true" CodeFile="GallerySingle.aspx.cs" Inherits="GallerySingle" %>

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

            <div class=" banner-buying">
                <div class=" container">
                    <h3><span><asp:Label ID="galleryName" runat="server" Text=""></asp:Label></span></h3>
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


        <div class="container">

            <div class="buy-single-single">

                <div class="col-md-9 single-box">
                    <asp:Label runat="server" ID="controlLabel"></asp:Label>
                    <!--ODABRANA GALERIJA-->
                    <div class=" buying-top">	
                        <div class="flexslider">
                            <ul class="slides">
                            <asp:PlaceHolder ID="PhotosOfGallery" runat="server">

                            </asp:PlaceHolder>
                            <!--
                                <li data-thumb="images/ss.jpg">
                                    <img src="images/ss.jpg" />
                                </li>-->   
                            </ul>
                        </div>
                        <!-- FlexSlider -->

                        <script defer src="js/jquery.flexslider.js"></script>
                        <link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen" />

                        <script>
                            // Can also be used with $(document).ready()
                            $(window).load(function () {
                                $('.flexslider').flexslider({
                                    animation: "slide",
                                    controlNav: "thumbnails"
                                });
                            });
                        </script>
                    </div>

                    <!--PODACI O VLASNIKU GALERIJE-->
                    <div class="buy-sin-single">
                        <div class="col-sm-5 middle-side immediate">
                            <h4>Podaci o vlasniku</h4>
                            <p><span class="bath">Ime i prezime</span>: <span class="two"><asp:Label ID="ownerName" runat="server" Text=""></asp:Label></span></p>
                            <p><span class="bath">Email</span>: <span class="two"><asp:Label ID="ownerEmail" runat="server" Text=""></asp:Label></span></p>
                            <p><span class="bath">Spol</span>: <span class="two"><asp:Label ID="ownerSex" runat="server" Text=""></asp:Label></span></p>
                            <p><span class="bath">Poruka:</span>: <span class="two"><asp:Label ID="ownerQuote" runat="server" Text=""></asp:Label></span></p>
                            <p><span class="bath">Broj objavljenih galerija:</span>: <span class="two"><asp:Label ID="numOfGalleries" runat="server" Text=""></asp:Label></span></p>

                            <div class="right-side">
                                <asp:PlaceHolder ID="galleryOwnerProfile" runat="server"></asp:PlaceHolder> 
                            </div>
                        </div>
                        <!--PODACI O GALERIJI-->
                        <div class="col-sm-7 buy-sin"">
                            <h4>Naziv galerije: 
                                <br /><span style="font-weight:normal; color:gray; font-size:0.7em;"><asp:Label ID="nameGall" runat="server" Text=""></asp:Label></span></h4>
                            <br/>
                            <h4>O galeriji: 
                                <br /><span style="font-weight:normal; color:gray; font-size:0.7em;"><asp:Label ID="gallDesc" runat="server" Text=""></asp:Label></span></h4>
                            <br/>
                            <h4>Datum objave:<br /><span style="font-weight:normal; color:gray; font-size:0.7em;"><asp:Label ID="gallDate" runat="server" Text=""></asp:Label> </span></h4>
                            <br/>
                            <h4>Kategorija galerije:<br /><span style="font-weight:normal; color:gray; font-size:0.7em;"><asp:Label ID="gallCategoryLbl" runat="server" Text=""></asp:Label> </span></h4>
                            <br/>
                            <h4>Broj <i class="glyphicon glyphicon-thumbs-up"></i>: <asp:Label ID="numOfLikes" runat="server" Text=""></asp:Label></h4>
                            <br/>
                            <asp:Button ID="BttnLike" CssClass="btn btn-primary" runat="server" OnClick="BttnLike_Click" Text="Lajkaj!" />
                            <asp:Button ID="BttnDeleteGallery" CssClass="btn btn-danger" runat="server" OnClick="BttnDeleteGallery_Click" Text="Obriši Galeriju!" OnClientClick="return confirm('Jeste sigurni da želite obrisati ovu galeriju?')" />
                            <asp:Label ID="errLike" CssClass="" runat="server" Text=""></asp:Label>
                        </div>

                        <div class="clearfix"> </div>
                    </div>

                </div>

                <div class="col-md-3">
                    <!--GALERIJE IZ ISTE KATEGORIJE-->
                    <div class="single-box-right right-immediate">
                        <h4>Galerije iz iste kategorije</h4>
                        <asp:Label ID="NoGalleriesFromThatCategory" runat="server" Text="Nema galerija iz ove kategorije"></asp:Label>
                        
                        <asp:PlaceHolder ID="galleriesCategory" runat="server">

                        </asp:PlaceHolder>
                        
                        <!--
                        <div class="single-box-img">
                            <div class="box-img">
                                <img class="img-responsive" src="images/sl.jpg" alt="">
                            </div>
                            <div class="box-text">
                                <p>NASLOV</p>
                                <a href="single.html" class="in-box">More Info</a>
                            </div>
                            <div class="clearfix"> </div>
                        </div>-->
                       

                    </div>

                </div>
                <div class="clearfix"> </div>
            </div>
        </div>

        <br/><br/><br/><br/><br/>

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
