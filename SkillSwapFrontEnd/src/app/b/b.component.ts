import {Component, OnInit} from '@angular/core';
import {CService} from '../c.service';

@Component({
  selector: 'app-b',
  imports: [],
  templateUrl: './b.component.html',
  standalone: true,
  styleUrl: './b.component.css'
})
export class BComponent implements OnInit{
  constructor(private service : CService) {
  }
    ngOnInit(): void {
  this.service.subject.subscribe((data)=>{
    console.log(data)
  })
    }

}
