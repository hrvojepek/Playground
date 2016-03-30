System.register(['angular2/core', 'angular2/router', "../../services/sneakers.service.component", "../../services/basket.service.component", "../../services/users.service.component"], function(exports_1, context_1) {
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
    var core_1, router_1, sneakers_service_component_1, basket_service_component_1, users_service_component_1;
    var HomeComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (sneakers_service_component_1_1) {
                sneakers_service_component_1 = sneakers_service_component_1_1;
            },
            function (basket_service_component_1_1) {
                basket_service_component_1 = basket_service_component_1_1;
            },
            function (users_service_component_1_1) {
                users_service_component_1 = users_service_component_1_1;
            }],
        execute: function() {
            HomeComponent = (function () {
                function HomeComponent(_sneakersService, _usersService, _basketService) {
                    this._sneakersService = _sneakersService;
                    this._usersService = _usersService;
                    this._basketService = _basketService;
                    this.isLoading = true;
                    this.isAuthenticated = false;
                    this._sneakersService = _sneakersService;
                    this.basket = [];
                }
                HomeComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    if (localStorage.getItem("token") != null) {
                        this.isAuthenticated = true;
                    }
                    console.log(this._usersService.isAuthenticatedUser());
                    this._sneakersService.getSneakers()
                        .subscribe(function (data) {
                        _this.sneakers = data;
                        localStorage.setItem('sneakers', JSON.stringify(_this.sneakers));
                        console.log(_this.sneakers);
                        _this.isLoading = false;
                    }, function (error) { return console.log(error); });
                    this._sneakersService.getSns();
                    this.basket = this._basketService.getBasketFromLS();
                };
                HomeComponent.prototype.addData = function () {
                };
                HomeComponent.prototype.addToBasket = function (s) {
                    this.basket.push(s);
                    console.log(this.basket);
                    this._basketService.saveBasketToLS(this.basket);
                };
                HomeComponent = __decorate([
                    core_1.Component({
                        selector: 'homeComponent',
                        templateUrl: 'app/components/main/home.html',
                        directives: [router_1.RouterLink],
                        providers: [sneakers_service_component_1.SneakersService, basket_service_component_1.BasketService, users_service_component_1.UsersService]
                    }), 
                    __metadata('design:paramtypes', [sneakers_service_component_1.SneakersService, users_service_component_1.UsersService, basket_service_component_1.BasketService])
                ], HomeComponent);
                return HomeComponent;
            }());
            exports_1("HomeComponent", HomeComponent);
        }
    }
});
//# sourceMappingURL=home.component.js.map