import { Routes } from '@angular/router';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {UserProfileComponent} from './components/user/user-profile/user-profile.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';

export const routes: Routes = [
  {path : 'login' , component : LoginComponent},
  {path : 'register' , component : RegisterComponent},
  {path : 'profile' , component : UserProfileComponent},
  {path : 'profile/:id' , component : UserProfileComponent},
  {path : 'profile/edit' , loadComponent: () => import('./components/user/user-profile-edit/user-profile-edit.component').then(m => m.UserProfileEditComponent)},
  {path : 'infos' , loadComponent: () => import('./components/user/user-info-page/user-info-page.component').then(m => m.UserInfoPageComponent)},
  {path : 'search' , component : SearchResultsComponent},
];
