import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { Observable, Subject } from 'rxjs';
import { Message } from '../models/message.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private stompClient: any;
  private messageSubject: Subject<Message> = new Subject<Message>();
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  connect(userId: number | undefined): void {
    const token = localStorage.getItem('token');
    const headers = { 'Authorization': `Bearer ${token}` };

    const socket = new SockJS(`${this.apiUrl}/ws`);
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect(headers, (frame: any) => {
      console.log('Connected: ' + frame);
      this.stompClient.subscribe(`/user/${userId}/queue/messages`, (message: any) => {
        this.messageSubject.next(JSON.parse(message.body));
      });
    });
  }

  sendMessage(message: Message): void {
    this.stompClient.send('/app/chat', {}, JSON.stringify(message));
  }

  getMessages(): Observable<Message> {
    return this.messageSubject.asObservable();
  }

  getConversation(senderId: number, receiverId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/messages/${senderId}/${receiverId}`);
  }

  getConversations(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/messages/conversations`);
  }

  sendHttpMessage(message: any): Observable<Message> {
    return this.http.post<Message>(`${this.apiUrl}/messages`, message);
  }
}
