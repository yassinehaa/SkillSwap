import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import {Router, RouterLink} from '@angular/router';
import {FormsModule, NgForm} from '@angular/forms';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [FormsModule, RouterLink, NgIf],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  firstName!: string;
  lastName!: string;
  email!: string;
  password!: string;

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit(form: NgForm): void {
    if (form.invalid) {
      Object.values(form.controls).forEach((control: any) => {
        control.markAsTouched();
      });
      return;
    }
    const user = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password,

    };
    this.authService.register(user).subscribe(() => {
      this.router.navigate(['/login']);
    });
  }
}

