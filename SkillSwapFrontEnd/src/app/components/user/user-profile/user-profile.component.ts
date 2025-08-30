import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { User } from '../../../models/user.model';
import { Skill } from '../../../models/skill.model';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;
  isEditing = false;
  profileForm: FormGroup;
  newProposedSkill = '';
  newSearchedSkill = '';
  errorMessage: string | null = null;

  constructor(private userService: UserService, private fb: FormBuilder, private route: ActivatedRoute) {
    this.profileForm = this.fb.group({
      isPremium: [false],
      proposedSkills: this.fb.array([]),
      searchedSkills: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const userId = params.get('id');
      if (userId) {
        this.loadUserProfile(Number(userId));
      } else {
        this.loadUserProfile();
      }
    });
  }

  // Get form arrays
  get proposedSkills(): FormArray {
    return this.profileForm.get('proposedSkills') as FormArray;
  }

  get searchedSkills(): FormArray {
    return this.profileForm.get('searchedSkills') as FormArray;
  }

  // Load user profile
  loadUserProfile(userId?: number): void {
    const userObservable = userId ? this.userService.getUserById(userId) : this.userService.getUser();
    userObservable.subscribe({
      next: (user) => {
        this.user = user;
        this.profileForm.patchValue({
          isPremium: user.isPremium
        });
        if (user.proposedSkills) {
          this.setSkills(user.proposedSkills, 'proposedSkills');
        }
        if (user.searchedSkills) {
          this.setSkills(user.searchedSkills, 'searchedSkills');
        }
        this.errorMessage = null;
      },
      error: (error) => {
        console.error('Error fetching user:', error);
        this.errorMessage = 'Failed to load user profile. Please try again.';
      }
    });
  }

  // Set skills in form array
  private setSkills(skills: Skill[], formArrayName: string): void {
    const formArray = this.profileForm.get(formArrayName) as FormArray;
    formArray.clear();
    skills.forEach(skill => formArray.push(this.fb.control(skill.name, Validators.required)));
  }

  // Add new skill
  addSkill(type: 'proposed' | 'searched', skill: string): void {
    if (skill.trim()) {
      const formArray = type === 'proposed' ? this.proposedSkills : this.searchedSkills;
      formArray.push(this.fb.control(skill.trim(), Validators.required));
      if (type === 'proposed') {
        this.newProposedSkill = '';
      } else {
        this.newSearchedSkill = '';
      }
    }
  }

  // Remove skill
  removeSkill(type: 'proposed' | 'searched', index: number): void {
    const formArray = type === 'proposed' ? this.proposedSkills : this.searchedSkills;
    formArray.removeAt(index);
  }

  // Submit form
  onSubmit(): void {
    if (this.profileForm.valid && this.user?.id) {
      const payload = {
        id: this.user.id,
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email, // Preserve existing email
        isPremium: this.profileForm.value.isPremium,
        proposedSkills: this.proposedSkills.value.map((name: string) => ({ name, type: 'OFFERED' })),
        searchedSkills: this.searchedSkills.value.map((name: string) => ({ name, type: 'SEARCHED' }))
      };
      this.userService.updateUser(payload).subscribe({
        next: (updatedUser) => {
          this.user = updatedUser;
          this.isEditing = false;
          this.errorMessage = null;
        },
        error: (error) => {
          console.error('Error updating profile:', error);
          this.errorMessage = 'Failed to update profile. Please try again.';
        }
      });
    } else {
      this.errorMessage = 'Cannot update profile: User ID is missing or form is invalid.';
    }
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    this.errorMessage = null;
  }
}
