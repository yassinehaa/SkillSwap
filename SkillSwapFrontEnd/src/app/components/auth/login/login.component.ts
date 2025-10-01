import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from "../../../services/auth.service";
import {Router, RouterLink} from "@angular/router";
import {response} from 'express';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  standalone: true,
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
        localStorage.setItem('token', response);
        this.router.navigate(['/profile']);
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
}
