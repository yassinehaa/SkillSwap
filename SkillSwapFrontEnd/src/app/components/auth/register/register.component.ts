import { Component } from '@angular/core';
import {User} from '../../../models/user.model';
import {FormsModule} from '@angular/forms';
import {AuthService} from "../../../services/auth.service";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    RouterLink
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

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.register(this.user).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Registration failed', error);
      }
    });
  }
}
