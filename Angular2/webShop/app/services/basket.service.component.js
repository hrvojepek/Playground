System.register(["angular2/core"], function(exports_1, context_1) {
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
    var core_1;
    var BasketService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            }],
        execute: function() {
            BasketService = (function () {
                function BasketService() {
                    this._basket = [];
                    this._basket = this.getBasketFromLS();
                    if (this._basket === null) {
                        this._basket = [];
                        this.saveBasketToLS(this._basket);
                    }
                }
                BasketService.prototype.getBasket = function () {
                    this._basket = this.getBasketFromLS();
                    if (this._basket === null) {
                        this._basket = [];
                        this.saveBasketToLS(this._basket);
                    }
                    return this._basket;
                };
                BasketService.prototype.setBasket = function (b) {
                    this._basket = b;
                    this.saveBasketToLS(b);
                };
                BasketService.prototype.getBasketFromLS = function () {
                    return JSON.parse(localStorage.getItem('basket'));
                };
                BasketService.prototype.saveBasketToLS = function (b) {
                    localStorage.setItem('basket', JSON.stringify(b));
                };
                BasketService.prototype.removeBasket = function () {
                    localStorage.removeItem('basket');
                };
                BasketService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [])
                ], BasketService);
                return BasketService;
            }());
            exports_1("BasketService", BasketService);
        }
    }
});
//# sourceMappingURL=basket.service.component.js.map