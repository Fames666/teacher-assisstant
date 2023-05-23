import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-journals-page',
  templateUrl: './admin-journals-page.component.html',
  styleUrls: ['./admin-journals-page.component.css']
})
export class AdminJournalsPageComponent implements OnInit {

  showJournalDialog = false;
  showJournal = false;

  showStudentDialog = false;

  constructor() { }

  ngOnInit() {
  }

  onAddJournal() {
    this.showJournalDialog = true;
  }

  onConfirmJournal() {
    setTimeout(_ => {
      this.showJournal = true;
      this.showJournalDialog = false;
    }, 400);
  }

  onAddStudent() {
    this.showStudentDialog = true;
  }

  onConfirmStudent() {
    setTimeout(_ => {
      this.showStudentDialog = false;
    }, 400);
  }
}
