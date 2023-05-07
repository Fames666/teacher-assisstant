import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { JournalsListService } from '../service/journals-list-service.service'
import { JournalPreview } from '../model/journals-list.model'
import { JournalUserPreview } from '../model/journals-list.model'

@Component({
  selector: '[page=journal]',
  templateUrl: './journals-list.component.html',
  styleUrls: ['./journals-list.component.css']
})
export class JournalsListComponent implements OnInit {

  journalsListHeader: string = "Список доступных журналов";
  journalsListHeaderHint: string = "(перетаскивайте для сортировки)";

  private _journalsPreviews: Array<JournalPreview>;
  private _journalUsersPreviews: Array<JournalUserPreview>;

  constructor(private service: JournalsListService) {
    this._journalsPreviews = [];
    this._journalUsersPreviews = [];
  }

  ngOnInit(): void {
    this.service.loadAvailableJournals("74148075-b59b-4ac4-860d-92a2dbf74abd", this.journalsPreviews);
    this.service.loadJournalUsers(2487, this.journalUsersPreviews);
  }

  public get journalsPreviews(): Array<JournalPreview> {
    return this._journalsPreviews;
  }

  public get journalUsersPreviews(): Array<JournalUserPreview> {
    return this._journalUsersPreviews;
  }
}
