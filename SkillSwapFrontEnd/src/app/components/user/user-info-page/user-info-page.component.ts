import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '../../../models/user.model';
import { UserService } from '../../../services/user.service';
import { UserProfileInfoComponent } from '../user-profile-info/user-profile-info.component';

@Component({
  selector: 'app-user-info-page',
  standalone: true,
  imports: [CommonModule, UserProfileInfoComponent],
  templateUrl: './user-info-page.component.html',
  styleUrls: ['./user-info-page.component.css']
})
export class UserInfoPageComponent implements OnInit {
  user!: User;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe(user => {
      this.user = user;
    });
  }
}