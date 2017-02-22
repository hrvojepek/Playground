/**
 * Created by Hrvoje on 19.5.2016..
 */
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var ionic_angular_1 = require('ionic-angular');
var user_service_1 = require("../../services/user.service");
var LoginPage = (function () {
    function LoginPage(nav, app, _userService) {
        this.nav = nav;
        this.app = app;
        this._userService = _userService;
        this._isLoading = false;
        this._passNotOK = false;
        this._emailNotOK = false;
    }
    LoginPage.prototype.loginUser = function (email, password) {
        var _this = this;
        this._isLoading = true;
        console.log(email + " " + password);
        var loginSuccessfull = true;
        this._userService.getUserByEmail(email)
            .subscribe(function (usr) {
            console.log(usr);
            if (usr.Id > 0) {
                _this._emailNotOK = false;
                if (password === usr.Password) {
                    _this._passNotOK = false;
                }
                else {
                    _this._passNotOK = true;
                    loginSuccessfull = false;
                }
            }
            else {
                _this._emailNotOK = true;
                loginSuccessfull = false;
            }
            if (loginSuccessfull) {
                var token = usr.Token;
                var name = usr.Name;
                var emailUsr = usr.Email;
                var photo = usr.UserPhoto;
                var usrId = usr.Id;
                _this._userService.loginUser(token, name, photo, emailUsr, usrId);
            }
            _this._isLoading = false;
        });
    };
    LoginPage = __decorate([
        ionic_angular_1.Page({
            templateUrl: 'build/pages/login.page/login.html',
            providers: [user_service_1.UserService]
        }), 
        __metadata('design:paramtypes', [ionic_angular_1.NavController, ionic_angular_1.IonicApp, user_service_1.UserService])
    ], LoginPage);
    return LoginPage;
}());
exports.LoginPage = LoginPage;
