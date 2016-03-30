/**
 * Created by Hrvoje on 29.3.2016..
 */
import {Injectable} from "angular2/core";
import 'rxjs/Rx';
import {Http, Headers} from "angular2/http";
import 'rxjs/add/operator/map';
import {Observable} from "rxjs/Observable";
import {User} from "../models/user.component";
import {Router} from "angular2/router";

@Injectable()
export class UsersService{
    users: User[];
    isAuthenticated : boolean = false;
    private _url: string = "https://sneakers-shop.firebaseio.com";

    constructor(private _http: Http, private _router: Router){}

    addData(data: any): Observable<any> {
        const body = JSON.stringify(data);
        const headers = new Headers();
        headers.append('Content-Type', 'application/json');
        return this._http.post("https://sneakers-shop.firebaseio.com/users/data.json", body, {headers: headers})
            .map(res => res.json());
    }

    getAllData(): Observable<any> {
        return this._http.get("https://sneakers-shop.firebaseio.com/users2/data.json")
            .map(res => res.json());
    }

    registerUser(user: User){
        return this._http.post("https://sneakers-shop.firebaseio.com/users2/data.json", JSON.stringify(user))
            .map(res => res.json());
    }
    
    singIn(user): boolean{
        var l: boolean = false;
        this.getAllData()
            .subscribe(
                data => {
                    this.users = data;
                    console.log(this.users);
                    console.log(this.users.length);
                    if(this.doesUserExist(data, user)){
                        console.log("ulazim, postoji user");
                        this.isAuthenticated = true;
                        localStorage.setItem("token", user.email);
                        this._router.navigate(['Home']);
                        l = true;
                    }
                    else{
                        console.log("ne postoji user");
                        l = false;
                    }
                },
                error => console.log(error)
            );
        return l;
    }

    isAuthenticatedUser(): boolean{
        return this.isAuthenticated;
    }

    doesUserExist(users: User[], user: User): boolean{

        for(var i=0;i<10;i++){
            if(users[i].email === user.email){
                return true;
            }
        }
        return false;
    }
}