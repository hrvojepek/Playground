System.register(["angular2/core", "../models/sneaker.component", 'rxjs/Rx', "angular2/http", 'rxjs/add/operator/map'], function(exports_1, context_1) {
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
    var core_1, sneaker_component_1, http_1;
    var SneakersService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (sneaker_component_1_1) {
                sneaker_component_1 = sneaker_component_1_1;
            },
            function (_1) {},
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (_2) {}],
        execute: function() {
            SneakersService = (function () {
                function SneakersService(_http) {
                    this._http = _http;
                    this._url = "https://sneakers-shop.firebaseio.com/sneakers";
                }
                SneakersService.prototype.getSneakers = function () {
                    return this._http.get("https://sneakers-shop.firebaseio.com/sneakers/data.json")
                        .map(function (res) { return res.json(); });
                };
                SneakersService.prototype.getSns = function () {
                    var _this = this;
                    this._http.get("https://sneakers-shop.firebaseio.com/sneakers/data.json")
                        .map(function (res) { return res.json(); })
                        .subscribe(function (data) {
                        _this.sneakers = data;
                    }, function (error) { return console.log(error); });
                };
                SneakersService.prototype.getSneakerById = function (id) {
                    var sneaker;
                    for (var i = 0; i < this.sneakers.length; i++) {
                        if (this.sneakers[i].id === id) {
                            sneaker = new sneaker_component_1.Sneaker(this.sneakers[i].id, this.sneakers[i].name, this.sneakers[i].description, this.sneakers[i].photo, this.sneakers[i].price);
                        }
                    }
                    console.log(sneaker);
                    return sneaker;
                };
                SneakersService.prototype.addData = function (data) {
                    var body = JSON.stringify(data);
                    var headers = new http_1.Headers();
                    headers.append('Content-Type', 'application/json');
                    return this._http.post("https://sneakers-shop.firebaseio.com/users/data.json", body, { headers: headers })
                        .map(function (res) { return res.json(); });
                };
                SneakersService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], SneakersService);
                return SneakersService;
            }());
            exports_1("SneakersService", SneakersService);
        }
    }
});
//# sourceMappingURL=sneakers.service.component.js.map