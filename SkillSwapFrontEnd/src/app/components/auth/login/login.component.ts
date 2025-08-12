import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    const credentials = { email: this.email, password: this.password };
    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        // Store the token and navigate to a protected route
        localStorage.setItem('token', response);
        this.router.navigate(['/']); // Navigate to home page for now
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
}
