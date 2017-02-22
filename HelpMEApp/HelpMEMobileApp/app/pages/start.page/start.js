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
var login_1 = require("../login.page/login");
var registration_1 = require("../registration.page/registration");
var StartPage = (function () {
    function StartPage(nav, menu) {
        this.nav = nav;
        this.menu = menu;
        this.menu.enable(false);
    }
    StartPage.prototype.goToRegistration = function () {
        this.nav.push(registration_1.RegistrationPage);
    };
    StartPage.prototype.goToLogin = function () {
        this.nav.push(login_1.LoginPage);
    };
    StartPage.prototype.facebookLogin = function () {
        console.log("FAcebook login");
    };
    StartPage = __decorate([
        ionic_angular_1.Page({
            templateUrl: 'build/pages/start.page/start.html',
            providers: []
        }), 
        __metadata('design:paramtypes', [ionic_angular_1.NavController, ionic_angular_1.MenuController])
    ], StartPage);
    return StartPage;
}());
exports.StartPage = StartPage;
