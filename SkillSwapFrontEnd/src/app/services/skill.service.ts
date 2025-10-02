import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Skill } from '../models/skill.model';

@Injectable({
  providedIn: 'root'
})
export class SkillService {
  private baseUrl = 'http://localhost:8080/api/skills';

  constructor(private http: HttpClient) { }

  getUserSkills(userId: number | undefined): Observable<Skill[]> {
    return this.http.get<Skill[]>(`${this.baseUrl}/user/${userId}`);
  }
}
