import {Component, OnInit} from 'angular2/core';
import {RouteConfig, RouterOutlet, RouterLink, Router} from 'angular2/router';


import {HomeComponent} from "./components/main/home.component";
import {BasketComponent} from "./components/basket/basket.component";
import {RegisterComponent} from "./components/register/register.component";
import {AuthService} from "./services/auth.service.component";
import {User} from "./models/user.component";
import {SneakerDetailComponent} from "./components/sneaker-detail/sneaker-detail.component";
import {Sneaker} from "./models/sneaker.component";
import {BasketService} from "./services/basket.service.component";

@RouteConfig([
    { path: '/home', name:'Home', component: HomeComponent},
    { path: '/basket', name:'Basket', component: BasketComponent},
    { path: '/register', name:'Register', component: RegisterComponent, useAsDefault: true},
    { path: '/sneaker-detail', name:'Sneaker-Detail', component: SneakerDetailComponent},
    { path: '/*other', name:'Other', redirectTo: ['Home']}
])


@Component({
    selector: 'my-app',
    templateUrl: 'app/app.component.html',
    directives: [RouterOutlet, RouterLink],
    providers: [AuthService, BasketService]
})
export class AppComponent implements OnInit{
    user: User;
    basket: Sneaker[];
    
    constructor(private _authService: AuthService, private _basketService: BasketService, private _router: Router){
        this.basket = [];
    }

    isAuthenticated(){
        return this._authService.isAuthenticated();

    }

    logout(){
        this._authService.logout();
    }

    ngOnInit(): any{
        this._authService.getLoggedOutUser().subscribe(()=>this._router.navigate(['Register']));
    }
}