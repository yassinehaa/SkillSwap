import { Routes } from '@angular/router';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {UserProfileComponent} from './components/user/user-profile/user-profile.component';

export const routes: Routes = [
  {path : 'login' , component : LoginComponent},
  {path : 'register' , component : RegisterComponent},
  {path : 'profile' , component : UserProfileComponent}
];
