/**
 * Created by Hrvoje on 28.3.2016..
 */
import {Component, OnInit} from 'angular2/core';
import {RouterLink} from 'angular2/router';
import {Sneaker} from "../../models/sneaker.component";
import {SneakersService} from "../../services/sneakers.service.component";
import {BasketService} from "../../services/basket.service.component";
import {UsersService} from "../../services/users.service.component";

@Component({
    selector: 'homeComponent',
    templateUrl: 'app/components/main/home.html',
    directives: [RouterLink],
    providers: [SneakersService, BasketService, UsersService]
})

export class HomeComponent implements OnInit{

    sneakers: Sneaker[];
    basket: Sneaker[];
    isLoading: boolean = true;
    isAuthenticated: boolean = false;
    
    constructor(private _sneakersService: SneakersService, private _usersService:UsersService, private _basketService: BasketService){
        this._sneakersService = _sneakersService;
        this.basket = [];
    }

    ngOnInit():void {

        if(localStorage.getItem("token")!=null){
            this.isAuthenticated = true;
        }
        console.log(this._usersService.isAuthenticatedUser());
        this._sneakersService.getSneakers()
            .subscribe(
                data => {
                    this.sneakers=data;
                    localStorage.setItem('sneakers', JSON.stringify(this.sneakers));
                    console.log(this.sneakers);
                    this.isLoading = false;
                },
                error => console.log(error)
            );
        this._sneakersService.getSns();
        this.basket = this._basketService.getBasketFromLS();
    }

    addData(){
        
    }

    addToBasket(s){
        this.basket.push(s);
        console.log(this.basket);

        this._basketService.saveBasketToLS(this.basket);
    }
}