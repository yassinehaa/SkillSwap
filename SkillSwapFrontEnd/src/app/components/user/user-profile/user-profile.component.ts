import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { User } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Skill } from '../../../models/skill.model';

import { ReportService } from '../../../services/report.service';
import { EvaluationService } from '../../../services/evaluation.service';

import { Request } from '../../../models/request.model';
import { RequestService } from '../../../services/request.service';

import { AuthService } from '../../../services/auth.service';
import { SkillService } from '../../../services/skill.service';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;
  currentUser: User | null = null;
  isEditing = false;
  profileForm: FormGroup;
  newProposedSkillName = '';
  newProposedSkillDescription = '';
  newProposedSkillLevel = '';
  newProposedSkillCategory = '';
  newSearchedSkillName = '';
  newSearchedSkillDescription = '';
  newSearchedSkillLevel = '';
  newSearchedSkillCategory = '';
  errorMessage: string | null = null;
  receivedRequests: Request[] = [];
  reportReason: string = '';
  userRating: number = 0;
  ratingComment: string = '';
  skills!: Skill[];

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private requestService: RequestService,
    private authService: AuthService,
    private reportService: ReportService,
    private evaluationService: EvaluationService,
    private skillService: SkillService
  ) {
    this.profileForm = this.fb.group({
      proposedSkills: this.fb.array([]),
      searchedSkills: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.getData();
  }

  getData(){
    this.route.data.subscribe(data => {
      this.user = data['user'];
      if (this.user) {
        this.profileForm.patchValue({
        });
        if (this.user.proposedSkills) {
          this.setSkills(this.user.proposedSkills, 'proposedSkills');
        }
        if (this.user.searchedSkills) {
          this.setSkills(this.user.searchedSkills, 'searchedSkills');
        }
      }
    });

    this.authService.getCurrentUser().subscribe((user: User) => {
      this.currentUser = user;
      if (this.currentUser && this.user && this.currentUser.id === this.user.id && this.user.id) {
        this.requestService.getRequests(this.user.id).subscribe(requests => {
          this.receivedRequests = requests;
        });
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

  // Set skills in form array
  private setSkills(skills: Skill[], formArrayName: string): void {
    const formArray = this.profileForm.get(formArrayName) as FormArray;
    formArray.clear();
    skills.forEach(skill =>
      formArray.push(this.fb.group({
        name: [skill.name, Validators.required],
        description: [skill.description, Validators.required],
        level: [skill.level, Validators.required],
        category: [skill.category, Validators.required],
        status: [skill.status]
      }))
    );
  }

  // Add new skill
  addSkill(type: 'proposed' | 'searched', description: string, level: string, category: string): void {
    if (type === 'proposed' && this.newProposedSkillName.trim()) {
      this.proposedSkills.push(this.fb.group({
        name: [this.newProposedSkillName.trim(), Validators.required],
        description: [description.trim(), Validators.required],
        level: [level.trim(), Validators.required],
        category: [category.trim(), Validators.required],
        status: ['PENDING']
      }));
    } else if (type === 'searched' && this.newSearchedSkillName.trim()) {
      this.searchedSkills.push(this.fb.group({
        name: [this.newSearchedSkillName.trim(), Validators.required],
        description: [description.trim(), Validators.required],
        level: [level.trim(), Validators.required],
        category: [category.trim(), Validators.required],
        status: ['PENDING']
      }));
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
      const payload: User = {
        ...this.user,
        proposedSkills: this.proposedSkills.value.map((skill: Skill) => ({ ...skill, type: 'OFFERED' })),
        searchedSkills: this.searchedSkills.value.map((skill: Skill) => ({ ...skill, type: 'SEARCHED' }))
      };

      this.userService.updateUser(payload).subscribe({
        next: () => {
          const userId = this.user?.id as number;
          this.userService.getUserById(userId).subscribe({
            next: (refreshedUser) => {
              this.user = refreshedUser;

              this.setSkills(this.user.proposedSkills || [], 'proposedSkills');
              this.setSkills(this.user.searchedSkills || [], 'searchedSkills');

              this.isEditing = false;
              this.errorMessage = null;
            },
            error: (err) => {
              console.error('Error refreshing user:', err);
              this.errorMessage = 'Profile updated but failed to reload user data.';
            }
          });
        },
        error: (error: any) => {
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

  requestToLearn(skill: Skill): void {
    if (this.currentUser && this.user) {
      const request = {
        requesterId: this.currentUser.id,
        receiverId: this.user.id,
        skillId: skill.id,
        status: 'PENDING'
      };
      this.requestService.sendRequest(request).subscribe({
        next: () => console.log('Request sent successfully'),
        error: (error: any) => console.error('Error sending request:', error)
      });
    }
  }

  private updateFormWithUserData() {
    if (this.user) {
      this.profileForm.patchValue({});
      if (this.user.proposedSkills) {
        this.setSkills(this.user.proposedSkills, 'proposedSkills');
      }
      if (this.user.searchedSkills) {
        this.setSkills(this.user.searchedSkills, 'searchedSkills');
      }
    }
  }

  acceptRequest(request: Request): void {
    this.requestService.acceptRequest(request).subscribe(() => {
      request.status = 'ACCEPTED';
    });
  }

  rejectRequest(request: Request): void {
    this.requestService.rejectRequest(request).subscribe(() => {
      request.status = 'REJECTED';
    });
  }

  submitReport(): void {
    if (this.user && this.currentUser && this.reportReason.trim() && this.currentUser.id && this.user.id) {
      const report = {
        reporterId: this.currentUser.id,
        reportedUserId: this.user.id,
        reason: this.reportReason.trim()
      };
      this.reportService.createReport(report).subscribe({
        next: () => {
          console.log('Report submitted successfully');
          this.reportReason = '';
        },
        error: (error: any) => console.error('Error submitting report:', error)
      });
    } else {
      console.error('Cannot submit report: Missing user, current user, or reason.');
    }
  }

  rateUser(rating: number): void {
    this.userRating = rating;
  }

  submitRating(): void {
    if (this.user && this.currentUser && this.userRating > 0) {
      const evaluation = {
        raterId: this.currentUser.id,
        ratedUserId: this.user.id,
        rating: this.userRating,
        comment: this.ratingComment.trim()
      };
      this.evaluationService.createEvaluation(evaluation).subscribe({
        next: (newEvaluation) => {
          console.log('Rating submitted successfully');
          this.userRating = 0;
          this.ratingComment = '';
          if (this.user) {
            this.user.evaluations = [...(this.user.evaluations || []), newEvaluation]; // Create a new array reference
          }
        },
        error: (error: any) => console.error('Error submitting rating:', error)
      });
    }
  }
}
