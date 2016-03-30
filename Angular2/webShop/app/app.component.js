System.register(['angular2/core', 'angular2/router', "./components/main/home.component", "./components/basket/basket.component", "./components/register/register.component", "./services/auth.service.component", "./components/sneaker-detail/sneaker-detail.component", "./services/basket.service.component"], function(exports_1, context_1) {
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
    var core_1, router_1, home_component_1, basket_component_1, register_component_1, auth_service_component_1, sneaker_detail_component_1, basket_service_component_1;
    var AppComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (home_component_1_1) {
                home_component_1 = home_component_1_1;
            },
            function (basket_component_1_1) {
                basket_component_1 = basket_component_1_1;
            },
            function (register_component_1_1) {
                register_component_1 = register_component_1_1;
            },
            function (auth_service_component_1_1) {
                auth_service_component_1 = auth_service_component_1_1;
            },
            function (sneaker_detail_component_1_1) {
                sneaker_detail_component_1 = sneaker_detail_component_1_1;
            },
            function (basket_service_component_1_1) {
                basket_service_component_1 = basket_service_component_1_1;
            }],
        execute: function() {
            AppComponent = (function () {
                function AppComponent(_authService, _basketService, _router) {
                    this._authService = _authService;
                    this._basketService = _basketService;
                    this._router = _router;
                    this.basket = [];
                }
                AppComponent.prototype.isAuthenticated = function () {
                    return this._authService.isAuthenticated();
                };
                AppComponent.prototype.logout = function () {
                    this._authService.logout();
                };
                AppComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    this._authService.getLoggedOutUser().subscribe(function () { return _this._router.navigate(['Register']); });
                };
                AppComponent = __decorate([
                    router_1.RouteConfig([
                        { path: '/home', name: 'Home', component: home_component_1.HomeComponent },
                        { path: '/basket', name: 'Basket', component: basket_component_1.BasketComponent },
                        { path: '/register', name: 'Register', component: register_component_1.RegisterComponent, useAsDefault: true },
                        { path: '/sneaker-detail', name: 'Sneaker-Detail', component: sneaker_detail_component_1.SneakerDetailComponent },
                        { path: '/*other', name: 'Other', redirectTo: ['Home'] }
                    ]),
                    core_1.Component({
                        selector: 'my-app',
                        templateUrl: 'app/app.component.html',
                        directives: [router_1.RouterOutlet, router_1.RouterLink],
                        providers: [auth_service_component_1.AuthService, basket_service_component_1.BasketService]
                    }), 
                    __metadata('design:paramtypes', [auth_service_component_1.AuthService, basket_service_component_1.BasketService, router_1.Router])
                ], AppComponent);
                return AppComponent;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map