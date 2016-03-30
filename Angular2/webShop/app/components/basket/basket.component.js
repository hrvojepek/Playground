System.register(['angular2/core', 'angular2/router', "../../services/basket.service.component"], function(exports_1, context_1) {
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
    var core_1, router_1, basket_service_component_1;
    var BasketComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (basket_service_component_1_1) {
                basket_service_component_1 = basket_service_component_1_1;
            }],
        execute: function() {
            BasketComponent = (function () {
                function BasketComponent(_basketService) {
                    this._basketService = _basketService;
                    this.sum = 0;
                }
                BasketComponent.prototype.ngOnInit = function () {
                    this.basket = this._basketService.getBasketFromLS();
                    for (var i = 0; i < this.basket.length; i++) {
                        this.sum += this.basket[i].price;
                    }
                };
                BasketComponent.prototype.clearBasket = function () {
                    this.basket = [];
                    this._basketService.removeBasket();
                    //this._basketService.saveBasketToLS(this.basket);
                };
                BasketComponent = __decorate([
                    core_1.Component({
                        selector: 'basketComponent',
                        templateUrl: 'app/components/basket/basket.html',
                        directives: [router_1.RouterLink],
                        providers: [basket_service_component_1.BasketService]
                    }), 
                    __metadata('design:paramtypes', [basket_service_component_1.BasketService])
                ], BasketComponent);
                return BasketComponent;
            }());
            exports_1("BasketComponent", BasketComponent);
        }
    }
});
//# sourceMappingURL=basket.component.js.map