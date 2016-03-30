System.register(["angular2/core", "angular2/router"], function(exports_1, context_1) {
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
    var core_1, router_1;
    var AuthService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            }],
        execute: function() {
            AuthService = (function () {
                function AuthService(_router) {
                    this._router = _router;
                    this._userLoggedOut = new core_1.EventEmitter();
                    this._userLoggedIn = new core_1.EventEmitter();
                    this._router = _router;
                }
                AuthService.prototype.singupUser = function (user) {
                    var firebaseRef = new Firebase("https://sneakers-shop.firebaseio.com");
                    firebaseRef.createUser({
                        email: user.email,
                        password: user.pass
                    }, function (error, userData) {
                        if (error) {
                            console.log(error);
                        }
                        else {
                            console.log('Created user: ' + userData.uid);
                        }
                    });
                };
                AuthService.prototype.singinUser = function (user) {
                    var firebaseRef = new Firebase("https://sneakers-shop.firebaseio.com");
                    firebaseRef.authWithPassword({
                        email: user.email,
                        password: user.pass
                    }, function (error, authData) {
                        if (error) {
                            console.log(error);
                        }
                        else {
                            localStorage.setItem('token', authData.token);
                            this._userLoggedIn.emit(null);
                        }
                    });
                };
                AuthService.prototype.logout = function () {
                    localStorage.removeItem('token');
                    this._userLoggedOut.emit(null);
                };
                AuthService.prototype.isAuthenticated = function () {
                    return localStorage.getItem('token') !== null;
                };
                AuthService.prototype.getLoggedOutUser = function () {
                    return this._userLoggedOut;
                };
                AuthService.prototype.getLoggedInUser = function () {
                    return this._userLoggedIn;
                };
                AuthService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [router_1.Router])
                ], AuthService);
                return AuthService;
            }());
            exports_1("AuthService", AuthService);
        }
    }
});
//# sourceMappingURL=auth.service.component.js.map