import {Component, Input, numberAttribute} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from '../../../services/message.service';
import { Message } from '../../../models/message.model';

@Component({
  selector: 'app-message-send',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './message-send.component.html',
  styleUrls: ['./message-send.component.css']
})
export class MessageSendComponent {
  @Input({transform: numberAttribute}) senderId!: number;
  @Input({transform: numberAttribute}) receiverId!: number;
  content: string = '';

  constructor(private messageService: MessageService) { }

  sendMessage(): void {
    if (this.content.trim()) {
      const message: Message = {
        senderId: this.senderId,
        receiverId: this.receiverId,
        content: this.content
      };
      this.messageService.sendMessage(message);
      this.content = '';
    }
  }
}
