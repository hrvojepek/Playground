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
var RegistrationPage = (function () {
    function RegistrationPage(nav, _userService) {
        this.nav = nav;
        this._userService = _userService;
        this._isLoading = false;
        this.hasErr = true;
        this.passMatch = true;
        this.nameEntered = false;
        this.emailEntered = false;
        this.passOk = false;
        this.passLengthOk = true;
    }
    RegistrationPage.prototype.registerUser = function (name, email, pass) {
        console.log(name + email + pass);
    };
    RegistrationPage.prototype.getData = function () {
        this._isLoading = true;
        this._userService.getUsers()
            .subscribe(function (data) {
            console.log(data);
        });
        this._isLoading = false;
    };
    //Functions for checking inputs
    RegistrationPage.prototype.checkName = function (name) {
        if (name != null && name != '') {
            this.nameEntered = true;
        }
        else {
            this.nameEntered = false;
        }
        this.checkValues();
    };
    RegistrationPage.prototype.checkEmail = function (email) {
        if (email != null && email != '') {
            this.emailEntered = true;
        }
        else {
            this.emailEntered = false;
        }
        this.checkValues();
    };
    RegistrationPage.prototype.checkPass = function (pass) {
        this.passLengthOk = false;
        this.passMatch = false;
        if (pass.length >= 4 && pass != '' && pass != null) {
            this.passOk = true;
            this.passLengthOk = true;
        }
        else {
            this.passOk = false;
            this.passLengthOk = false;
        }
        this.checkValues();
    };
    RegistrationPage.prototype.checkPasswords = function (pass, passConf) {
        this.passMatch = false;
        if (pass === passConf) {
            this.passMatch = true;
        }
        else {
            this.passMatch = false;
        }
        this.checkValues();
    };
    RegistrationPage.prototype.checkValues = function () {
        if (this.nameEntered && this.emailEntered && this.passOk && this.passMatch) {
            this.hasErr = false;
        }
        else {
            this.hasErr = true;
        }
    };
    RegistrationPage = __decorate([
        ionic_angular_1.Page({
            templateUrl: 'build/pages/registration.page/registration.html',
            providers: [user_service_1.UserService]
        }), 
        __metadata('design:paramtypes', [ionic_angular_1.NavController, user_service_1.UserService])
    ], RegistrationPage);
    return RegistrationPage;
}());
exports.RegistrationPage = RegistrationPage;
