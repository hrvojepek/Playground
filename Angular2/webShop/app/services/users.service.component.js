System.register(["angular2/core", 'rxjs/Rx', "angular2/http", 'rxjs/add/operator/map', "angular2/router"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, router_1;
    var UsersService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (_1) {},
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (_2) {},
            function (router_1_1) {
                router_1 = router_1_1;
            }],
        execute: function() {
            UsersService = (function () {
                function UsersService(_http, _router) {
                    this._http = _http;
                    this._router = _router;
                    this.isAuthenticated = false;
                    this._url = "https://sneakers-shop.firebaseio.com";
                }
                UsersService.prototype.addData = function (data) {
                    var body = JSON.stringify(data);
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    return this._http.post("https://sneakers-shop.firebaseio.com/users/data.json", body, { headers: headers })
                        .map(function (res) { return res.json(); });
                };
                UsersService.prototype.getAllData = function () {
                    return this._http.get("https://sneakers-shop.firebaseio.com/users2/data.json")
                        .map(function (res) { return res.json(); });
                };
                UsersService.prototype.registerUser = function (user) {
                    return this._http.post("https://sneakers-shop.firebaseio.com/users2/data.json", JSON.stringify(user))
                        .map(function (res) { return res.json(); });
                };
                UsersService.prototype.singIn = function (user) {
                    var _this = this;
                    var l = false;
                    this.getAllData()
                        .subscribe(function (data) {
                        _this.users = data;
                        console.log(_this.users);
                        console.log(_this.users.length);
                        if (_this.doesUserExist(data, user)) {
                            console.log("ulazim, postoji user");
                            _this.isAuthenticated = true;
                            localStorage.setItem("token", user.email);
                            _this._router.navigate(['Home']);
                            l = true;
                        }
                        else {
                            console.log("ne postoji user");
                            l = false;
                        }
                    }, function (error) { return console.log(error); });
                    return l;
                };
                UsersService.prototype.isAuthenticatedUser = function () {
                    return this.isAuthenticated;
                };
                UsersService.prototype.doesUserExist = function (users, user) {
                    for (var i = 0; i < 10; i++) {
                        if (users[i].email === user.email) {
                            return true;
                        }
                    }
                    return false;
                };
                UsersService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http, router_1.Router])
                ], UsersService);
                return UsersService;
            }());
            exports_1("UsersService", UsersService);
        }
    }
});
//# sourceMappingURL=users.service.component.js.map