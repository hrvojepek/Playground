/**
 * Created by Hrvoje on 28.3.2016..
 */
import {Injectable} from "angular2/core";
import {Sneaker} from "../models/sneaker.component";
import 'rxjs/Rx';
import {Http, Headers} from "angular2/http";
import 'rxjs/add/operator/map';
import {Observable} from "rxjs/Observable";

@Injectable()
export class SneakersService{
    sneakers;
    private _url: string = "https://sneakers-shop.firebaseio.com/sneakers";

    constructor(private _http: Http){}


    getSneakers(){
        return this._http.get("https://sneakers-shop.firebaseio.com/sneakers/data.json")
            .map(res => res.json());
    }

    getSns(){

            this._http.get("https://sneakers-shop.firebaseio.com/sneakers/data.json")
                .map(res => res.json())
                .subscribe(
                data => {
                    this.sneakers=data;
                },
                error => console.log(error)
            )
    }

    getSneakerById(id: number): Sneaker{
        var sneaker: Sneaker;

            for(var i=0; i<this.sneakers.length;i++){
                if(this.sneakers[i].id === id){
                    sneaker = new Sneaker(this.sneakers[i].id, this.sneakers[i].name, this.sneakers[i].description, this.sneakers[i].photo, this.sneakers[i].price);
                    //id: number, name: string, description: string, photo: string, price: number
                }
            }
        console.log(sneaker);
        return sneaker;
    }


    addData(data: any): Observable<any> {
        const body = JSON.stringify(data);
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this._http.post("https://sneakers-shop.firebaseio.com/users/data.json", body, {headers: headers})
            .map(res => res.json());
    }
}