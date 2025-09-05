import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-request-skill',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './request-skill.component.html',
  styleUrls: ['./request-skill.component.css']
})
export class RequestSkillComponent {
  request = {
    skillId: null,
    message: '',
    paymentMethod: 'skill_exchange' // or 'paypal'
  };

  constructor(private router: Router) {}

  onSubmit(): void {
    console.log('Skill request submitted:', this.request);
    // Here you would typically call a service to save the skill request
    // and send a message if the payment method is paypal
    this.router.navigate(['/']);
  }

  cancel(): void {
    this.router.navigate(['/']);
  }
}
