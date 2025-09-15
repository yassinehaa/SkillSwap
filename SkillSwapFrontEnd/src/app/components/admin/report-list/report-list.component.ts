
import { Component, OnInit } from '@angular/core';
import { ReportService } from '../../../services/report.service';
import { Report } from '../../../models/report.model';
import { CommonModule, NgForOf, NgIf} from "@angular/common";
import {AComponent} from '../../../a/a.component';
import {BComponent} from '../../../b/b.component';

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html',
  standalone: true,
  imports: [
    NgForOf,
    CommonModule,
    AComponent,
    BComponent
  ],
  styleUrls: ['./report-list.component.css']
})
export class ReportListComponent implements OnInit {
  reports: Report[] = [];

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.getAllReports();
  }

  getAllReports(): void {
    this.reportService.getAllReports().subscribe((reports: Report[]) => {
      this.reports = reports;
    });
  }
}
