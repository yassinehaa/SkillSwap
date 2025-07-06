import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillMatchComponent } from './skill-match.component';

describe('SkillMatchComponent', () => {
  let component: SkillMatchComponent;
  let fixture: ComponentFixture<SkillMatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SkillMatchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SkillMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
