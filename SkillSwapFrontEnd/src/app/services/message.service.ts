import { Injectable } from '@angular/core';
import {Message} from 'postcss';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) {}

  sendMessage(senderId: number, receiverId: number, message: Message): Observable<Message> {
    return this.http.post<Message>(`${this.apiUrl}/send/${senderId}/${receiverId}`, message);
  }

  getConversation(userId1: number, userId2: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/conversation/${userId1}/${userId2}`);
  }
}
