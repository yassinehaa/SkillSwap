import { Routes } from '@angular/router';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {UserProfileComponent} from './components/user/user-profile/user-profile.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { RequestListComponent } from './components/requests/request-list/request-list.component';
import { UserResolver } from './resolvers/user.resolver';
import { MessagesComponent } from './components/messages/messages.component';
import { SkillExchangeComponent } from './components/skill-exchange/skill-exchange.component';

import { AdminDashboardComponent } from './components/admin/admin-dashboard/admin-dashboard.component';
import { ReportListComponent } from './components/admin/report-list/report-list.component';

export const routes: Routes = [
  {path : 'login' , component : LoginComponent},
  {path : 'register' , component : RegisterComponent},
  {path : 'profile' , component : UserProfileComponent, resolve: { user: UserResolver }},
  {path : 'profile/:id' , component : UserProfileComponent, resolve: { user: UserResolver }},
  {path : 'profile/edit' , loadComponent: () => import('./components/user/user-profile-edit/user-profile-edit.component').then(m => m.UserProfileEditComponent)},
  {path : 'infos' , loadComponent: () => import('./components/user/user-info-page/user-info-page.component').then(m => m.UserInfoPageComponent)},
  {path : 'search' , component : SearchResultsComponent},
  {path : 'requests' , component : RequestListComponent},
  {path : 'messages' , component : MessagesComponent},
  
  {path : 'request-skill' , loadComponent: () => import('./components/requests/request-skill/request-skill.component').then(m => m.RequestSkillComponent)},
  {path : 'skill-exchange/:id' , component : SkillExchangeComponent},
  
  {path : 'admin/dashboard' , component : AdminDashboardComponent},
  {path : 'admin/reports' , component : ReportListComponent},
];
