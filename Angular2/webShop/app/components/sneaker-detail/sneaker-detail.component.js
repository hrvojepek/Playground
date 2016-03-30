System.register(["angular2/core", "angular2/router", "../../services/users.service.component", "../../services/sneakers.service.component", "../../models/sneaker.component"], function(exports_1, context_1) {
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
    var core_1, router_1, users_service_component_1, sneakers_service_component_1, sneaker_component_1;
    var SneakerDetailComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (users_service_component_1_1) {
                users_service_component_1 = users_service_component_1_1;
            },
            function (sneakers_service_component_1_1) {
                sneakers_service_component_1 = sneakers_service_component_1_1;
            },
            function (sneaker_component_1_1) {
                sneaker_component_1 = sneaker_component_1_1;
            }],
        execute: function() {
            SneakerDetailComponent = (function () {
                function SneakerDetailComponent(_sneakersService, _routeParams) {
                    this._sneakersService = _sneakersService;
                    this._routeParams = _routeParams;
                    this.isLoading = true;
                }
                SneakerDetailComponent.prototype.ngOnInit = function () {
                    var id = parseInt(this._routeParams.get("id"));
                    console.log(this._routeParams.get("id"));
                    var tempSneakers = localStorage.getItem("sneakers");
                    this.sneakers = JSON.parse(tempSneakers);
                    console.log(this.sneakers);
                    console.log(this.sneakers.length);
                    for (var i = 0; i < this.sneakers.length; i++) {
                        if (this.sneakers[i].id === id) {
                            this.selectedSneaker = new sneaker_component_1.Sneaker(this.sneakers[i].id, this.sneakers[i].name, this.sneakers[i].description, this.sneakers[i].photo, this.sneakers[i].price);
                            //id: number, name: string, description: string, photo: string, price: number
                            this.isLoading = false;
                            console.log(this.selectedSneaker);
                        }
                    }
                };
                SneakerDetailComponent = __decorate([
                    core_1.Component({
                        selector: 'sneakerDetailComponent',
                        templateUrl: 'app/components/sneaker-Detail/sneakerDetail.html',
                        directives: [router_1.RouterLink],
                        providers: [users_service_component_1.UsersService, sneakers_service_component_1.SneakersService]
                    }), 
                    __metadata('design:paramtypes', [sneakers_service_component_1.SneakersService, router_1.RouteParams])
                ], SneakerDetailComponent);
                return SneakerDetailComponent;
            }());
            exports_1("SneakerDetailComponent", SneakerDetailComponent);
        }
    }
});
//# sourceMappingURL=sneaker-detail.component.js.map