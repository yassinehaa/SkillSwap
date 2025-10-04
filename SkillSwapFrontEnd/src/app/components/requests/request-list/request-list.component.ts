
import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { Request } from '../../../models/request.model';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user.model';

import { Router } from '@angular/router';
import {NgForOf, NgIf} from "@angular/common";
import {filter} from 'rxjs';

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  imports: [
    NgForOf,
    NgIf
  ],
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {
  requests: Request[] = [];
  sentRequests: Request[] = [];
  currentUser: User | null = null;

  constructor(private requestService: RequestService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      if (this.currentUser && this.currentUser.id) {
        this.requestService.getRequests(this.currentUser.id).subscribe(requests => {
          this.requests = requests;
        });
        this.requestService.getSentRequests(this.currentUser.id).subscribe(sentRequests => {
          this.sentRequests = sentRequests;
        });
      }
    });
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


}
