import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, FormArray, FormControl } from '@angular/forms';
import { User } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { Skill, SkillType } from '../../../models/skill.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './user-profile-edit.component.html',
  styleUrls: ['./user-profile-edit.component.css']
})
export class UserProfileEditComponent implements OnInit {

  user!: User;
  profileForm!: FormGroup;
  isLoading = true;

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      proposedSkills: this.fb.array([]),
      searchedSkills: this.fb.array([])
    });

    this.userService.getUser().subscribe(user => {
      this.user = user;
      this.profileForm.patchValue({
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        email: this.user.email
      });
      this.setSkills();
      this.isLoading = false;
    });
  }

  private setSkills(): void {
    this.user.proposedSkills?.forEach(skill => this.proposedSkills.push(new FormControl(skill.name)));
    this.user.searchedSkills?.forEach(skill => this.searchedSkills.push(new FormControl(skill.name)));
  }

  get proposedSkills(): FormArray {
    return this.profileForm.get('proposedSkills') as FormArray;
  }

  get searchedSkills(): FormArray {
    return this.profileForm.get('searchedSkills') as FormArray;
  }

  addProposedSkill(): void {
    setTimeout(() => {
      this.proposedSkills.push(new FormControl(''));
    });
  }

  removeProposedSkill(index: number): void {
    this.proposedSkills.removeAt(index);
  }

  addSearchedSkill(): void {
    setTimeout(() => {
      this.searchedSkills.push(new FormControl(''));
    });
  }

  removeSearchedSkill(index: number): void {
    this.searchedSkills.removeAt(index);
  }

  saveProfile(): void {
    if (this.profileForm.valid && this.user) {
      const updatedUser: User = {
        ...this.user,
        ...this.profileForm.value,
        id: this.user.id, // Ensure the user ID is included
        proposedSkills: this.proposedSkills.controls.map(control => ({ name: control.value, type: SkillType.OFFERED })),
        searchedSkills: this.searchedSkills.controls.map(control => ({ name: control.value, type: SkillType.SEARCHED }))
      };
      this.userService.updateUser(updatedUser).subscribe(() => {
        this.router.navigate(['/profile']);
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/profile']);
  }
}