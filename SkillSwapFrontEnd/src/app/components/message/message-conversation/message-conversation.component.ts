import {DatePipe, NgClass, NgForOf} from '@angular/common';
import { Component, Input } from '@angular/core';
import {Message} from 'postcss';

@Component({
  selector: 'app-message-conversation',
  imports: [
    NgClass,
    NgForOf,
    DatePipe
  ],
  templateUrl: './message-conversation.component.html',
  standalone: true,
  styleUrl: './message-conversation.component.css'
})
export class MessageConversationComponent {
  @Input() messages: Message[] = [];
  @Input() currentUserId!: number;
  @Input() currentUserAvatar: string = 'assets/default-avatar.jpg';
  @Input() contactAvatar: string = 'assets/default-avatar.jpg';

  getAvatar(senderId: number): string {
    return senderId === this.currentUserId ? this.currentUserAvatar : this.contactAvatar;
  }
}
