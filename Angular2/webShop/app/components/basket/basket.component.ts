/**
 * Created by Hrvoje on 28.3.2016..
 */
import {Component, OnInit} from 'angular2/core';
import {RouterLink} from 'angular2/router';
import {Sneaker} from "../../models/sneaker.component";
import {BasketService} from "../../services/basket.service.component";

@Component({
    selector: 'basketComponent',
    templateUrl: 'app/components/basket/basket.html',
    directives: [RouterLink],
    providers: [BasketService]
})

export class BasketComponent implements OnInit{
    basket: Sneaker[];
    sum: number = 0;

    constructor(private _basketService: BasketService){}

    ngOnInit(){
        this.basket = this._basketService.getBasketFromLS();

        for(var i=0; i<this.basket.length;i++){
            this.sum += this.basket[i].price;
        }
    }

    clearBasket(){
        this.basket = [];
        this._basketService.removeBasket();
        //this._basketService.saveBasketToLS(this.basket);
    }

}