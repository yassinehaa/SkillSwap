import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {AuthService} from "../../../services/auth.service";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    RouterLink
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
        // TODO: Implement logic to determine whether to redirect to home or profile
        // For now, redirecting to /profile as an example.
        this.router.navigate(['/profile']);
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
}
