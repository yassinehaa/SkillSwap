import { MessageConversationComponent } from '../../message/message-conversation/message-conversation.component';
import { MessageSendComponent } from '../../message/message-send/message-send.component';
import { AuthService } from '../../../services/auth.service';
import {CommonModule} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Skill} from '../../../models/skill.model';

import { MessageService } from '../../../services/message.service';

import { Request } from '../../../models/request.model';
import { RequestService } from '../../../services/request.service';
import { PaypalComponent } from '../../payment/paypal/paypal.component';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, MessageConversationComponent, MessageSendComponent, PaypalComponent],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;
  currentUser: User | null = null;
  isEditing = false;
  profileForm: FormGroup;
  newProposedSkill = '';
  newSearchedSkill = '';
  errorMessage: string | null = null;
  receivedRequests: Request[] = [];

  constructor(private userService: UserService, private fb: FormBuilder, private route: ActivatedRoute, private messageService: MessageService, private requestService: RequestService, private authService: AuthService, private router: Router) {
    this.profileForm = this.fb.group({
      isPremium: [false],
      proposedSkills: this.fb.array([]),
      searchedSkills: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.user = data['user'];
      if (this.user) {
        this.profileForm.patchValue({
          isPremium: this.user.isPremium
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

  requestToLearn(skill: Skill): void {
    if (this.currentUser && this.user) {
      const request = {
        requesterId: this.currentUser.id,
        receiverId: this.user.id,
        skillId: skill.id,
        status: 'PENDING'
      };
      this.requestService.sendRequest(request).subscribe({
        next: () => {
          console.log('Request sent successfully');
        },
        error: (error) => {
          console.error('Error sending request:', error);
        }
      });
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

  startConversation(userId: number | undefined): void {
    if (userId) {
      this.router.navigate(['/messages', userId]);
    }
  }
}
