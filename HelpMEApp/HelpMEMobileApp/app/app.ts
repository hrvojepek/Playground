import {App, IonicApp, Platform} from 'ionic-angular';
import {StatusBar} from 'ionic-native';
import {StartPage} from "./pages/start.page/start";
import {SpinnerComponent} from "./help-components/spinner-component/spinner.component";


@App({
  templateUrl: 'build/app.html',
  config: {}, // http://ionicframework.com/docs/v2/api/config/Config/
  directives: [SpinnerComponent]
})
class MyApp {
  rootPage: any = StartPage;
  pages: Array<{icon: string, title: string, component: any}>

  constructor(private app: IonicApp, private platform: Platform) {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [
      { icon: 'fa fa-user', title: 'My Profile', component: StartPage }
    ];

    this.rootPage = StartPage;

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

  logout(): void{
    let nav = this.app.getComponent('nav');
    nav.setRoot(StartPage);
  }
}
