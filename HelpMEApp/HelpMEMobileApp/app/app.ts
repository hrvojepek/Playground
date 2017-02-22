import {App, IonicApp, Platform} from 'ionic-angular';
import {StatusBar} from 'ionic-native';
import {StartPage} from "./pages/start.page/start";
import {SpinnerComponent} from "./help-components/spinner-component/spinner.component";
import {UsersService} from "./services/user-service/user.service";
import {HomePage} from "./pages/home.page/home";
import {DonationService} from "./services/donation-service/donation.service";
import {HumEventService} from "./services/humEvents-service/hum.event.service";
import {AttendingEventService} from "./services/humEvents-service/attending.event.service";
import {CommentEventService} from "./services/humEvents-service/comment.event.service";
import {WantDonationService} from "./services/donation-service/want.donation.service";
import {CommentDonationService} from "./services/donation-service/comment.donation.service";


@App({
  templateUrl: 'build/app.html',
  config: {}, // http://ionicframework.com/docs/v2/api/config/Config/
  directives: [SpinnerComponent],
  providers: [UsersService, DonationService, HumEventService, AttendingEventService, CommentEventService, WantDonationService, CommentDonationService]
})
class MyApp {
  rootPage: any = StartPage;
  pages: Array<{icon: string, title: string, component: any}>

  user: any = {"photo": '', "email": '', "name": ''};

  constructor(private app: IonicApp, private platform: Platform, private _userService: UsersService) {
    this.initializeApp();

    this._userService.userHasLoggedIn.subscribe(u=>this.setUser(u));

    // used for an example of ngFor and navigation
    this.pages = [
      { icon: 'fa fa-home', title: 'Home', component: HomePage },
      { icon: 'fa fa-user', title: 'My Profile', component: StartPage }
    ];

    if(localStorage.getItem('token')!=null){
      this.rootPage = HomePage;
      this.user = {"photo": localStorage.getItem('usrPhoto'), "email": localStorage.getItem('usrEmail'), "name": localStorage.getItem('usrName')};
      console.log(this.user);
    }
    else {
      this.rootPage = StartPage;
    }

  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      StatusBar.styleDefault();
    });
  }

  openPage(page) {
    // Reset the content nav to have just this page
    // we wouldn't want the back button to show in this scenario
    let nav = this.app.getComponent('nav');
    nav.setRoot(page.component);
  }

  setUser(user): void{
      this.user = user;
      console.log(this.user);
  }

  logout(): void{
    let success = this._userService.logoutUser();
    if(success){
        let nav = this.app.getComponent('nav');
        nav.setRoot(StartPage);
    }
  }
}
