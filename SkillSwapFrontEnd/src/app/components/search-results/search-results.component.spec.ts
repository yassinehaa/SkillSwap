import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { SearchResultsComponent } from './search-results.component';
import { SearchService, SkillSearchResult } from '../../services/search.service';
import { User } from '../../models/user.model';
import { Skill } from '../../models/skill.model';

describe('SearchResultsComponent', () => {
  let component: SearchResultsComponent;
  let fixture: ComponentFixture<SearchResultsComponent>;
  let searchService: SearchService;

  const mockUser: User = {
    id: 1,
    username: 'testuser',
    email: 'test@example.com',
    password: 'password',
    firstname: 'Test',
    lastname: 'User',
    dateOfBirth: new Date(),
    profilePicture: '',
    description: '',
    userSkills: []
  };

  const mockSkill: Skill = {
    id: 1,
    name: 'Angular',
    description: 'Web framework',
    user: mockUser,
    skillLevel: 'Expert'
  };

  const mockSearchResults: SkillSearchResult[] = [
    { skill: mockSkill, user: mockUser }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        FormsModule,
        SearchResultsComponent
      ],
      providers: [SearchService]
    }).compileComponents();

    fixture = TestBed.createComponent(SearchResultsComponent);
    component = fixture.componentInstance;
    searchService = TestBed.inject(SearchService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call searchSkills on search', () => {
    spyOn(searchService, 'searchSkills').and.returnValue(of(mockSearchResults));
    component.name = 'Angular';
    component.onSearch();
    expect(searchService.searchSkills).toHaveBeenCalledWith('Angular');
  });

  it('should set results on successful search', () => {
    spyOn(searchService, 'searchSkills').and.returnValue(of(mockSearchResults));
    component.name = 'Angular';
    component.onSearch();
    expect(component.results).toEqual(mockSearchResults);
  });

  it('should set searchError to true on search error', () => {
    spyOn(searchService, 'searchSkills').and.returnValue(throwError(() => new Error('Error')));
    component.name = 'Angular';
    component.onSearch();
    expect(component.searchError).toBe(true);
  });

  it('should display error message on search error', () => {
    spyOn(searchService, 'searchSkills').and.returnValue(throwError(() => new Error('Error')));
    component.name = 'Angular';
    component.onSearch();
    fixture.detectChanges();
    const errorMessage = fixture.nativeElement.querySelector('.error-message');
    expect(errorMessage).toBeTruthy();
  });

  it('should display "No results found" message when no results are returned', () => {
    spyOn(searchService, 'searchSkills').and.returnValue(of([]));
    component.name = 'Angular';
    component.onSearch();
    fixture.detectChanges();
    const noResultsMessage = fixture.nativeElement.querySelector('.no-results-message');
    expect(noResultsMessage).toBeTruthy();
  });
});
