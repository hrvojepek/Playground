/**
 * Created by Hrvoje on 29.3.2016..
 */
import {Component, OnInit} from "angular2/core";
import {RouterLink, RouteParams} from "angular2/router";
import {UsersService} from "../../services/users.service.component";
import {SneakersService} from "../../services/sneakers.service.component";
import {Sneaker} from "../../models/sneaker.component";


@Component({
    selector: 'sneakerDetailComponent',
    templateUrl: 'app/components/sneaker-Detail/sneakerDetail.html',
    directives: [RouterLink],
    providers: [UsersService, SneakersService]
})

export class SneakerDetailComponent implements OnInit{
    selectedSneaker: Sneaker;
    sneakers;
    isLoading: boolean = true;

    constructor(private _sneakersService: SneakersService, private _routeParams: RouteParams){}

    ngOnInit(){
        var id: number = parseInt(this._routeParams.get("id"));
        console.log(this._routeParams.get("id"));

        var tempSneakers = localStorage.getItem("sneakers");
        this.sneakers = JSON.parse(tempSneakers);
        console.log(this.sneakers);
        console.log(this.sneakers.length);

            for(var i=0; i<this.sneakers.length;i++){
                if(this.sneakers[i].id === id){
                    this.selectedSneaker = new Sneaker(this.sneakers[i].id, this.sneakers[i].name, this.sneakers[i].description, this.sneakers[i].photo, this.sneakers[i].price);
                    //id: number, name: string, description: string, photo: string, price: number
                    this.isLoading = false;
                    console.log(this.selectedSneaker);
                }
            }

    }
}
