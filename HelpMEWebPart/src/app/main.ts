/**
 * Created by Hrvoje on 24.05.2016..
 */
import {Component} from '@angular/core';
import {RouteConfig, ROUTER_DIRECTIVES} from '@angular/router-deprecated';
import {HeaderComponent} from "./help-components/header/header.component";
import {HomePage} from "./pages/home/home.page";
import {FooterComponent} from "./help-components/footer/footer.component";
import {LoginPage} from "./pages/login/login.page";
import {RegisterPage} from "./pages/register/register.page";
import {UserService} from "./services/user-service/UserService";
import {HowItWorksPage} from "./pages/how-it-works/how.it.works.page";
import {ContactPage} from "./pages/contact/contact";
import {FeedbackPage} from "./pages/feedback/feedback";
import {MyProfilePage} from "./pages/my-profile/my.profile.page";
import {AddDonationPage} from "./pages/add-donation/add.donation";
import {DonationDetailsPage} from "./pages/donation-details/donation.details";
import {HumEventDetailsPage} from "./pages/humEvent-details/event.details";
import {UserDetailsPage} from "./pages/other-user-details/user.details.page";

@Component({
  selector: 'my-app',
  providers: [UserService],
  pipes: [],
  directives: [ROUTER_DIRECTIVES, HeaderComponent, FooterComponent],
  templateUrl: 'app/main.html',
})

@RouteConfig([
  { path: '/home', component: HomePage, name: 'Home', useAsDefault: true  },
  { path: '/login', component: LoginPage, name: 'Login'},
  { path: '/register', component: RegisterPage, name: 'Register' },
  { path: '/my-profile', component: MyProfilePage, name: 'MyProfile' },
  { path: '/about', component: HowItWorksPage, name: 'About' },
  { path: '/contact', component: ContactPage, name: 'Contact' },
  { path: '/feedback', component: FeedbackPage, name: 'Feedback' },
  { path: '/add-donation', component: AddDonationPage, name: 'AddDonation' },
  {path: '/donation/:id', name: 'DonationDetails', component: DonationDetailsPage},
  {path: '/event/:id', name: 'EventDetails', component: HumEventDetailsPage},
  {path: '/user-details/:id', name: 'UserDetails', component: UserDetailsPage}
])

export class MainPage {

  constructor() {}

}

