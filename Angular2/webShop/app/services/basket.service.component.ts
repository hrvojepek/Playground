/**
 * Created by Hrvoje on 29.3.2016..
 */
import {Injectable} from "angular2/core";
import {Sneaker} from "../models/sneaker.component";

@Injectable()
export class BasketService {
    private _basket: Sneaker[] = [];

    constructor(){
        this._basket = this.getBasketFromLS();
        if(this._basket===null){
            this._basket = [];
            this.saveBasketToLS(this._basket);
        }
    }

    getBasket(){
        this._basket = this.getBasketFromLS();
        if(this._basket===null){
            this._basket = [];
            this.saveBasketToLS(this._basket);
        }
        return this._basket;
    }

    setBasket(b: Sneaker[]){
        this._basket = b;
        this.saveBasketToLS(b);
    }

    getBasketFromLS(){
        return JSON.parse(localStorage.getItem('basket'));
    }

    saveBasketToLS(b){
        localStorage.setItem('basket', JSON.stringify(b));
    }

    removeBasket(){
        localStorage.removeItem('basket');
    }

}
