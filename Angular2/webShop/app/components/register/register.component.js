System.register(["angular2/core", "angular2/router", "../../services/users.service.component", "../../services/auth.service.component", "../../models/user.component", "../../services/sneakers.service.component"], function(exports_1, context_1) {
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
    var core_1, router_1, users_service_component_1, auth_service_component_1, user_component_1, sneakers_service_component_1;
    var RegisterComponent;
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
            function (auth_service_component_1_1) {
                auth_service_component_1 = auth_service_component_1_1;
            },
            function (user_component_1_1) {
                user_component_1 = user_component_1_1;
            },
            function (sneakers_service_component_1_1) {
                sneakers_service_component_1 = sneakers_service_component_1_1;
            }],
        execute: function() {
            RegisterComponent = (function () {
                function RegisterComponent(_usersService, _router, _sneakersService, _authService) {
                    this._usersService = _usersService;
                    this._router = _router;
                    this._sneakersService = _sneakersService;
                    this._authService = _authService;
                    this.isLoading = false;
                }
                RegisterComponent.prototype.getUsers = function () {
                    var _this = this;
                    this._usersService.getAllData()
                        .subscribe(function (data) {
                        //console.log(data);
                        _this.users = data;
                        console.log(_this.users);
                    }, function (error) { return console.log(error); });
                    /*setTimeout(function() {
                        this.setUsers();
                    }, 2000);*/
                };
                /*setUsers(){
            
                    for(var i=0;i<this.dataset.length; i++){
                        console.log("Name: " + this.dataset[i].name);
                    }
                }*/
                RegisterComponent.prototype.register = function (form) {
                    var name = form.value['name'];
                    var pass = form.value['password'];
                    var email = form.value['email'];
                    var user = new user_component_1.User(name, email, pass);
                    console.log(user);
                    //this._authService.singupUser(user);
                    this._usersService.registerUser(user)
                        .subscribe(function (data) { return console.log(data); }, function (error) { return console.log(error); });
                };
                RegisterComponent.prototype.login = function (form) {
                    //this.isLoading = true;
                    var pass = form.value['password'];
                    var email = form.value['email'];
                    var name = "";
                    var user = new user_component_1.User(name, email, pass);
                    console.log(user);
                    this.isLoading = true;
                    //this._authService.singinUser(user);
                    this._usersService.singIn(user);
                    /*var token = localStorage.getItem('token');
                    //this.isLoading = false;
                    this._router.navigate(['Home']);
                    if(token!==null){
                        this._router.navigate(['Home']);
                    }*/
                };
                RegisterComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    this._authService.getLoggedInUser().subscribe(function () { return _this._router.navigate(['Home']); });
                };
                RegisterComponent = __decorate([
                    core_1.Component({
                        selector: 'registerComponent',
                        templateUrl: 'app/components/register/register.html',
                        directives: [router_1.RouterLink],
                        providers: [users_service_component_1.UsersService, sneakers_service_component_1.SneakersService]
                    }), 
                    __metadata('design:paramtypes', [users_service_component_1.UsersService, router_1.Router, sneakers_service_component_1.SneakersService, auth_service_component_1.AuthService])
                ], RegisterComponent);
                return RegisterComponent;
            }());
            exports_1("RegisterComponent", RegisterComponent);
        }
    }
});
//# sourceMappingURL=register.component.js.map