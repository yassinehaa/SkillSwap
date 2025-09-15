import { Component } from '@angular/core';
import {CService} from '../c.service';
import {UserService} from '../services/user.service';

interface countSkill {
  name : string;
  skillcount: number;
}
@Component({
  selector: 'app-a',
  imports: [],
  templateUrl: './a.component.html',
  standalone: true,
  styleUrl: './a.component.css'
})
export class AComponent {

  constructor(private cservice : CService, private userservice: UserService) {
  }
  b !: countSkill[];
  a : string = "test";

  onClick() {
    this.cservice.subject.next(this.a)
    this.countSkill()
  }

  countSkill(){
    this.userservice.skillCount().subscribe((data)=>{this.b=data
    console.log(this.b)
    }
    )
  }
}
