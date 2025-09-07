import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {
  private apiUrl = 'http://localhost:8080/api/evaluations';

  constructor(private http: HttpClient) { }

  createEvaluation(evaluation: any): Observable<any> {
    return this.http.post(this.apiUrl, evaluation);
  }
}