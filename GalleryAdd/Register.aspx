<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Register.aspx.cs" Inherits="Register" %>

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
            $("#passwordTxtBox").keyup(passLength);
        });

        function passLength() {
            var password1 = $("#passwordTxtBox").val();

            if (password1.length < 5) {
                document.getElementById("validate-status0").style.color = "red";
                $("#validate-status0").text("Lozinka mora sadržavati minimalno 5 znakova!");
                document.getElementById("bttnReg").disabled = true;
            }

            else {
                document.getElementById("validate-status0").style.color = "green";
                $("#validate-status0").text("Lozinka prihvatljiva!");
                document.getElementById("bttnReg").disabled = false;
            }


        }

        $(document).ready(function () {
            $("#passwordTxtBox2").keyup(validate);
        });


        function validate() {
            var password1 = $("#passwordTxtBox").val();
            var password2 = $("#passwordTxtBox2").val();

            if (password1 == password2) {
                document.getElementById("validate-status").style.color = "green";
                $("#validate-status").text("Lozinke se podudaraju");
                document.getElementById("bttnReg").disabled = false;
            }
            else {
                document.getElementById("validate-status").style.color = "red";
                $("#validate-status").text("Lozinke se ne podudaraju");
                document.getElementById("bttnReg").disabled = true;
            }

        }
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
                    <h3><span>REGISTRACIJA KORISNIKA</span></h3>
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
                <h3>Podaci Registracije</h3>
                <div class="login-top">

                    <label>Ime</label>
                    <asp:TextBox runat="server" ID="fNameTxtBox" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidatorFNameReg" runat="server" CssClass="errorLogin" ErrorMessage="Morate unijeti ime!" ControlToValidate="fNameTxtBox"></asp:RequiredFieldValidator>
                    <br />

                    <label>Prezime</label>
                    <asp:TextBox runat="server" ID="lNameTxtBox" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidatorLNameReg" runat="server" CssClass="errorLogin" ErrorMessage="Morate unijeti prezime!" ControlToValidate="lNameTxtBox"></asp:RequiredFieldValidator>
                    <br />

                    <label>Email</label>
                    <asp:TextBox runat="server" ID="emailTxtBox" CssClass="form-control" />
                    <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ErrorMessage="Unesite ispravan email!" ControlToValidate="emailTxtBox" CssClass="errorLogin" ValidationExpression="^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$"></asp:RegularExpressionValidator>
                    <asp:RequiredFieldValidator ID="RequiredFieldValidatorEmailReg" runat="server" CssClass="errorLogin" ErrorMessage="Morate unijeti mail!" ControlToValidate="emailTxtBox"></asp:RequiredFieldValidator>
                    <br />

                    <label>Poruka (nalazi se na profilu ispod slike)</label>
                    <asp:TextBox runat="server" ID="messageTxtBox" CssClass="form-control" />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidatorMsgReg" runat="server" CssClass="errorLogin" ErrorMessage="Morate unijeti poruku!" ControlToValidate="messageTxtBox"></asp:RequiredFieldValidator>
                    <br />

                    <label>Spol:</label>&nbsp;&nbsp;
                        <asp:RadioButtonList ID="sex" runat="server">
                            <asp:ListItem Text="Muško" Value="Muško" />
                            <asp:ListItem Text="Žensko" Value="Žensko" />
                        </asp:RadioButtonList>
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" CssClass="errorLogin" ErrorMessage="Morate odabrati spol!" ControlToValidate="messageTxtBox"></asp:RequiredFieldValidator>
                    <br />

                    <div class="form-group">
                        <label>Slika profila</label>
                        <asp:FileUpload ID="profilePicture" runat="server" accept=".png,.jpg,.jpeg,.gif" />
                        <br />
                    </div>

                    <label>Lozinka</label>
                    <asp:TextBox runat="server" ID="passwordTxtBox" CssClass="form-control" TextMode="Password" />
                    <p class="errorLogin" id="validate-status0">
                        <asp:RequiredFieldValidator ID="RequiredFieldValidatorPassReg" CssClass="errorLogin" runat="server" ErrorMessage="Morate unijeti lozinku!" ControlToValidate="passwordTxtBox"></asp:RequiredFieldValidator>
                    </p>
                    <br />

                    <label>Ponovite Lozinku</label>
                    <asp:TextBox runat="server" ID="passwordTxtBox2" CssClass="form-control" TextMode="Password" />
                    <p class="errorLogin" id="validate-status">
                        <asp:RequiredFieldValidator ID="RequiredFieldValidatorPassReg2" CssClass="errorLogin" runat="server" ErrorMessage="Morate ponoviti lozinku!" ControlToValidate="passwordTxtBox2"></asp:RequiredFieldValidator>
                    </p>

                    <br />
                    <asp:CheckBox ID="CheckBoxLoginNow" runat="server" />
                    &nbsp;
                        <label>Ulogiraj me odmah nakon registracije!</label>
                    <br />
                    <br />
                    <asp:Button runat="server" ID="bttnReg" CssClass="btn btn-primary" OnClick="Register_Click" Text="Registriraj me!" Width="117px" />

                    <br />
                    <br />
                    <asp:Label ID="labelError" CssClass="errorLogin" runat="server" Text=""></asp:Label>

                    <div class="create">
                        <h4>Imate korisnički račun?</h4>
                        <a class="hvr-sweep-to-right" href="Login.aspx">Login</a>
                        <div class="clearfix"></div>
                    </div>
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
