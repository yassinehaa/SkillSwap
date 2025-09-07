
import { Component, OnInit } from '@angular/core';
import { RequestService } from '../../../services/request.service';
import { Request } from '../../../models/request.model';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user.model';
import { MessageService } from '../../../services/message.service';
import { Router } from '@angular/router';
import {NgForOf, NgIf} from "@angular/common";

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
  currentUser: User | null = null;

  constructor(private requestService: RequestService, private authService: AuthService, private messageService: MessageService, private router: Router) { }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      if (this.currentUser && this.currentUser.id) {
        this.requestService.getRequests(this.currentUser.id).subscribe(requests => {
          this.requests = requests;
        });
      }
    });
  }

  acceptRequest(request: Request): void {
    this.requestService.acceptRequest(request).subscribe(() => {
      request.status = 'ACCEPTED';
      const message = {
        senderId: this.currentUser?.id,
        receiverId: request.requester.id,
        content: `Your request to learn ${request.skill.name} has been accepted.`
      };
      this.messageService.sendHttpMessage(message).subscribe();
    });
  }

  rejectRequest(request: Request): void {
    this.requestService.rejectRequest(request).subscribe(() => {
      request.status = 'REJECTED';
      const message = {
        senderId: this.currentUser?.id,
        receiverId: request.requester.id,
        content: `Your request to learn ${request.skill.name} has been rejected.`
      };
      this.messageService.sendHttpMessage(message).subscribe();
    });
  }

  acceptWithSkillExchange(request: Request): void {
    this.router.navigate(['/skill-exchange', request.id]);
  }

  acceptWithPayPal(request: Request): void {
    const message = {
      senderId: this.currentUser?.id,
      receiverId: request.requester.id,
      content: `Your request to learn ${request.skill.name} has been accepted. Please proceed with the payment.`
    };
    this.messageService.sendHttpMessage(message).subscribe(() => {
      this.router.navigate(['/paypal-payment', request.id]);
    });
  }
}
