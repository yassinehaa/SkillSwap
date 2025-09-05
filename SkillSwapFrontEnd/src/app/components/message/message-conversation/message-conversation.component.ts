import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../../../services/message.service';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user.model';
import { Message } from '../../../models/message.model';
import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-message-conversation',
  templateUrl: './message-conversation.component.html',
  styleUrls: ['./message-conversation.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, NgForOf, NgIf]
})
export class MessageConversationComponent implements OnInit, OnDestroy {
  receiverId: number | null = null;
  currentUser: User | null = null;
  messages: Message[] = [];
  newMessageContent: string = '';
  private messageSubscription: Subscription | undefined;

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      this.route.paramMap.subscribe(params => {
        const id = params.get('userId');
        if (id) {
          this.receiverId = +id;
          this.loadConversation();
          this.messageService.connect(this.currentUser?.id);
          this.messageSubscription = this.messageService.getMessages().subscribe(message => {
            if ((message.senderId === this.currentUser?.id && message.receiverId === this.receiverId) ||
                (message.senderId === this.receiverId && message.receiverId === this.currentUser?.id)) {
              this.messages.push(message);
            }
          });
        }
      });
    });
  }

  ngOnDestroy(): void {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
  }

  loadConversation(): void {
    if (this.currentUser && this.receiverId) {
      this.messageService.getConversation(this.currentUser.id!, this.receiverId).subscribe(messages => {
        this.messages = messages;
      });
    }
  }

  sendMessage(): void {
    if (this.newMessageContent.trim() && this.currentUser && this.receiverId) {
      const message: Message = {
        senderId: this.currentUser.id!,
        receiverId: this.receiverId,
        content: this.newMessageContent
      };
      this.messageService.sendHttpMessage(message).subscribe(
        (sentMessage) => {
          this.messages.push(sentMessage);
          this.newMessageContent = '';
        },
        (error) => {
          console.error('Error sending message:', error);
        }
      );
    }
  }
}