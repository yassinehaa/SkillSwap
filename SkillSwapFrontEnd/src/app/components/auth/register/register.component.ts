import { Component } from '@angular/core';
import {User} from '../../../models/user.model';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user: User = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    isPremium: false
  };

  onSubmit() {
    console.log('Registration attempted with:', this.user);
  }
}
