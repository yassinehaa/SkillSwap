
import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { Message } from '../../models/message.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  imports: [
    NgForOf,
    RouterLink,
    DatePipe
  ],
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {
  currentUser: User | null = null;
  conversations: any[] = [];

  constructor(private messageService: MessageService, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      if (this.currentUser) {
        this.messageService.getConversations().subscribe(conversations => {
          this.conversations = conversations;
          console.log('Conversations:', this.conversations);
        });
      }
    });
  }
}
