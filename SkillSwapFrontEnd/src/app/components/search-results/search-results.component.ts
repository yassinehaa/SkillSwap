import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';   // ✅ needed for ngModel
import { SearchService } from '../../services/search.service';
import { SkillSearchResultDTO } from '../../models/skill-search-result.dto';

import {ActivatedRoute, RouterLink} from '@angular/router';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],  // ✅ include FormsModule
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  name: string = '';
  results: SkillSearchResultDTO[] = [];

  constructor(private searchService: SearchService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.name = params['q'];
      this.search();
    });
  }

  private search(): void {
    if (this.name && this.name.trim() !== '') {
      this.searchService.searchSkills(this.name).subscribe(skills => {
        this.results = skills;
        console.log(skills);
      });
    } else {
      this.results = [];
    }
  }
}
