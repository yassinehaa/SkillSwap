import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

interface Skill {
  name: string;
  category: string;
  description: string;
  level: number;
}

@Component({
  selector: 'app-skill-add',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './skill-add.component.html',
  styleUrl: './skill-add.component.css'
})
export class SkillAddComponent {
  skill: Skill = {
    name: '',
    category: '',
    description: '',
    level: 3
  };

  constructor(private router: Router) {}

  onSubmit(): void {
    // Here you would typically call a service to save the skill
    console.log('Skill submitted:', this.skill);
    // Navigate back to skills list or profile page
    this.router.navigate(['/skills']);
  }

  cancel(): void {
    // Navigate back without saving
    this.router.navigate(['/skills']);
  }
}
