
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Request } from '../models/request.model';

import { MessageService } from './message.service';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private baseUrl = 'http://localhost:8080/api/requests';

  constructor(private http: HttpClient, private messageService: MessageService) { }

  sendRequest(request: any): Observable<any> {
    if (request.paymentMethod === 'paypal') {
      const message = {
        receiverId: request.receiverId, // assuming you have receiverId in the request object
        content: 'I would like to pay with PayPal. Please provide your PayPal address.'
      };
      this.messageService.sendHttpMessage(message).subscribe();
    }
    return this.http.post(this.baseUrl, request);
  }

  getRequests(receiverId: number): Observable<Request[]> {
    return this.http.get<Request[]>(`${this.baseUrl}/received/${receiverId}`);
  }

  acceptRequest(request: Request): Observable<Request> {
    return this.http.put<Request>(`${this.baseUrl}/${request.id}/accept`, {});
  }

  rejectRequest(request: Request): Observable<Request> {
    return this.http.put<Request>(`${this.baseUrl}/${request.id}/reject`, {});
  }
}
