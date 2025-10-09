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
  };

  constructor(private router: Router) {}

  onSubmit(): void {
  }

  cancel(): void {
    this.router.navigate(['/']);
  }
}
