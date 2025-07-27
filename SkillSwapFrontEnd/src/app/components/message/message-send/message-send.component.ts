import {Component, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import EventEmitter from 'node:events';

@Component({
  selector: 'app-message-send',
  imports: [
    FormsModule
  ],
  templateUrl: './message-send.component.html',
  styleUrl: './message-send.component.css'
})
export class MessageSendComponent {
  @Output() messageSent = new EventEmitter<string>();
  messageText: string = '';

  send() {
    if (this.messageText.trim()) {
      this.messageSent.emit(this.messageText.trim());
      this.messageText = '';
    }
  }
}
