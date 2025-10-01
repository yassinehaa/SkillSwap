
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Request } from '../models/request.model';
import { Skill } from '../models/skill.model';

import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private baseUrl = 'http://localhost:8080/api/requests';

  constructor(private http: HttpClient) { }

  sendRequest(request: any): Observable<any> {

    return this.http.post(this.baseUrl, request);
  }

  getRequests(receiverId: number): Observable<Request[]> {
    return this.http.get<Request[]>(`${this.baseUrl}/received/${receiverId}`);
  }

  getRequest(id: number): Observable<Request> {
    return this.http.get<Request>(`${this.baseUrl}/${id}`);
  }

  acceptRequest(request: Request): Observable<Request> {
    return this.http.put<Request>(`${this.baseUrl}/${request.id}/accept`, {});
  }

  rejectRequest(request: Request): Observable<Request> {
    return this.http.put<Request>(`${this.baseUrl}/${request.id}/reject`, {});
  }




}
