
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RequestService } from '../../services/request.service';
import { Request } from '../../models/request.model';
import { Skill } from '../../models/skill.model';
import { SkillService } from '../../services/skill.service';
import { MessageService } from '../../services/message.service';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-skill-exchange',
  templateUrl: './skill-exchange.component.html',
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  styleUrls: ['./skill-exchange.component.css']
})
export class SkillExchangeComponent implements OnInit {
  request: Request | null = null;
  requesterSkills: Skill[] = [];
  selectedSkill: Skill | null = null;
  message: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private requestService: RequestService,
    private skillService: SkillService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    const requestId = this.route.snapshot.paramMap.get('id');
    if (requestId) {
      this.requestService.getRequest(+requestId).subscribe(request => {
        this.request = request;
        if (this.request) {
          this.skillService.getUserSkills(this.request.requester.id).subscribe((skills: Skill[]) => {
            this.requesterSkills = skills;
          });
        }
      });
    }
  }

  selectSkill(skill: Skill): void {
    this.selectedSkill = skill;
  }

  confirmExchange(): void {
    if (this.request && this.selectedSkill) {
      this.requestService.acceptRequestWithSkillExchange(this.request, this.selectedSkill).subscribe(() => {
        const message = {
          senderId: this.request?.receiver.id,
          receiverId: this.request?.requester.id,
          content: `I have accepted your request to learn ${this.request?.skill.name} and I would like to exchange it for your skill: ${this.selectedSkill?.name}. ${this.message}`
        };
        this.messageService.sendHttpMessage(message).subscribe(() => {
          this.router.navigate(['/requests']);
        });
      });
    }
  }
}
