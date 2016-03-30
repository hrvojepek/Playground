/**
 * Created by Hrvoje on 29.3.2016..
 */
import {Component, OnInit} from "angular2/core";
import {RouterLink, Router} from "angular2/router";
import {UsersService} from "../../services/users.service.component";
import {AuthService} from "../../services/auth.service.component";
import {User} from "../../models/user.component";
import {SneakersService} from "../../services/sneakers.service.component";
import {bootstrap} from "angular2/bootstrap";


@Component({
    selector: 'registerComponent',
    templateUrl: 'app/components/register/register.html',
    directives: [RouterLink],
    providers: [UsersService, SneakersService]
})

export class RegisterComponent implements OnInit{
    users: User[];
    sneakers: any;
    isLoading: boolean = false;

    constructor(private _usersService: UsersService, private _router: Router, private _sneakersService: SneakersService, private _authService: AuthService){}

    getUsers(){
        this._usersService.getAllData()
            .subscribe(
                data => {
                    //console.log(data);
                    this.users = data;
                    console.log(this.users);
                },
                error => console.log(error)
            );
        /*setTimeout(function() {
            this.setUsers();
        }, 2000);*/
    }

    /*setUsers(){

        for(var i=0;i<this.dataset.length; i++){
            console.log("Name: " + this.dataset[i].name);
        }
    }*/

    register(form){
        var name = form.value['name'];
        var pass = form.value['password'];
        var email = form.value['email'];

        var user = new User(name,email,pass);

        console.log(user);

        //this._authService.singupUser(user);

        this._usersService.registerUser(user)
            .subscribe(
                data=>console.log(data),
                error=>console.log(error)
            );
    }

    login(form){
        //this.isLoading = true;
        var pass = form.value['password'];
        var email = form.value['email'];
        var name = "";

        var user = new User(name,email,pass);

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
    }

    ngOnInit(){
        this._authService.getLoggedInUser().subscribe(()=>this._router.navigate(['Home']));
    }

}