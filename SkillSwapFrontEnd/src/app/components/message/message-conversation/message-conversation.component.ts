import {Component, Input, numberAttribute, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Message } from '../../../models/message.model';
import { MessageService } from '../../../services/message.service';

@Component({
  selector: 'app-message-conversation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message-conversation.component.html',
  styleUrls: ['./message-conversation.component.css']
})
export class MessageConversationComponent implements OnInit {
  @Input({transform: numberAttribute}) userId!: number;
  @Input({transform: numberAttribute}) otherUserId!: number;
  messages: Message[] = [];

  constructor(private messageService: MessageService) { }

  ngOnInit(): void {
    this.loadConversation();
    this.messageService.getMessages().subscribe((message: Message) => {
      if ((message.senderId === this.userId && message.receiverId === this.otherUserId) || (message.senderId === this.otherUserId && message.receiverId === this.userId)) {
        this.messages.push(message);
      }
    });
  }

  loadConversation(): void {
    this.messageService.getConversation(this.userId, this.otherUserId).subscribe(messages => {
      this.messages = messages;
    });
  }
}
