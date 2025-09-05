import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLoggedIn = false;
  username = 'User';
  
  // This would typically come from an auth service
  constructor() {
    // For demo purposes, we'll just set a mock logged in state
    // In a real app, you would inject an AuthService and check the login status
    this.isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    const storedUsername = localStorage.getItem('username');
    if (storedUsername) {
      this.username = storedUsername;
    }
  }
  
  logout(): void {
    // In a real app, you would call your auth service logout method
    this.isLoggedIn = false;
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('username');
    // Typically you would navigate to the home page or login page after logout
  }
}
