import { SkillSearchResultDTO } from '../models/skill-search-result.dto';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private apiUrl = " http://localhost:8080/api/skills/search";


  constructor(private http: HttpClient) {
  }

  searchSkills(name: string): Observable<SkillSearchResultDTO[]> {
    const url = `${this.apiUrl}?name=${name}`
    return this.http.get<SkillSearchResultDTO[]>(url);
  }
}

