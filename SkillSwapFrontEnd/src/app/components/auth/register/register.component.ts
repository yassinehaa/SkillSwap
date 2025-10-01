import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import {Router, RouterLink} from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  standalone: true,
  imports: [FormsModule, RouterLink],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  firstName!: string;
  lastName!: string;
  email!: string;
  password!: string;

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit(): void {
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

