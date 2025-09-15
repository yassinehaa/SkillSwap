
import { Component, OnInit } from '@angular/core';
import { MessageService } from '../../services/message.service';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { Message } from '../../models/message.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {ActivatedRoute, RouterLink} from '@angular/router';
import { MessageConversationComponent } from '../message/message-conversation/message-conversation.component';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  imports: [
    NgForOf,
    RouterLink,
    DatePipe,
    MessageConversationComponent,
    NgIf
  ],
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit {
  currentUser: User | null = null;
  conversations: any[] = [];
  selectedConversation: any = null;

  constructor(private messageService: MessageService, private authService: AuthService, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      if (this.currentUser) {
        this.messageService.getConversations().subscribe(conversations => {
          this.conversations = conversations;
          this.route.queryParams.subscribe(params => {
            const userId = params['userId'];
            if (userId) {
              const conversation = this.conversations.find(c => c.withUser.id == userId);
              if (conversation) {
                this.selectConversation(conversation);
              } else {
                this.userService.getUserById(userId).subscribe(withUser => {
                  const newConversation = {
                    withUser: withUser,
                    lastMessage: { content: '' }
                  };
                  this.conversations.unshift(newConversation);
                  this.selectConversation(newConversation);
                });
              }
            }
          });
        });
      }
    });
  }

  selectConversation(conversation: any): void {
    this.selectedConversation = conversation;
  }
}
