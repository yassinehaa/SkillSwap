import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-user-profile-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile-info.component.html',
  styleUrls: ['./user-profile-info.component.css']
})
export class UserProfileInfoComponent {
  @Input() user!: User;
}