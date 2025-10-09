import { Component } from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {AuthService} from "../../../services/auth.service";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    RouterLink,
    NgIf
  ],
  templateUrl: './login.component.html',
  standalone: true,
  styleUrl: './login.component.css'
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) {}
  onSubmit(form: NgForm) {
    if (form.invalid) {
      Object.values(form.controls).forEach((control: any) => {
        control.markAsTouched();
      });
      return;
    }
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
