import { Component, OnInit } from '@angular/core';
import { JournalsListService } from './service/journals-list-service.service'
import { JournalPreview, JournalUserPreview } from './model/journals-list.model'
import { Subject } from 'rxjs';
import { JournalsListModuleConstants } from 'src/app/journals-list/common/journals-list.common'
import { JournalListEvent } from 'src/app/journals-list/common/journals-list.common'

@Component({
  selector: '[page=journals-list]',
  templateUrl: './journals-list.component.html',
  styleUrls: ['./journals-list.component.css']
})
export class JournalsListComponent implements OnInit {

  private static readonly DUMMIES_INDEXES: ReadonlyArray<number> = Array(10).map((_, i) => i);
  private static readonly NOT_SELECTED = -1;

  public journalsPreviews: Array<JournalPreview>;
  public journalUsersPreviews: Array<JournalUserPreview>;
  public journalsListHeader: string;
  public journalsListHeaderHint: string;
  public journalStudentsAwaitingDummyText: string;
  public usersListLoaded: boolean;
  public showUsersList: boolean;

  private _journalListObservable: Subject<JournalListEvent>;
  private _selectedJournalId: number;

  constructor(private service: JournalsListService) {
    this._journalListObservable = new Subject<JournalListEvent>();
    this._selectedJournalId = JournalsListComponent.NOT_SELECTED;

    this.journalsPreviews = [];
    this.journalUsersPreviews = [];
    this.usersListLoaded = false;
    this.showUsersList = false;

    // TODO: add i18n
    this.journalsListHeader = "Список доступных журналов";
    this.journalsListHeaderHint = "(перетаскивайте для сортировки)";
    this.journalStudentsAwaitingDummyText = "Нажмите на журнал для просмотра списка пользователей";
  }

  public ngOnInit(): void {
    this.service.loadAvailableJournals("74148075-b59b-4ac4-860d-92a2dbf74abd", this.journalsPreviews);
  }

  public onJournalSelected(journalId: number): void {
    if (this._selectedJournalId === journalId) {
      journalId = JournalsListComponent.NOT_SELECTED;
      this.showUsersList = false;
    }
    else {
      this.showUsersList = true;
    }
    this.usersListLoaded = false;
    this._selectedJournalId = journalId;

    this._journalListObservable.next(new JournalListEvent(
      JournalsListModuleConstants.UPDATE_CLICKED_STATE_EVENT_CODE,
      journalId
    ));
  }

  public onUsersLoaded(loadedUsers: Array<JournalUserPreview>): void {
    if (loadedUsers !== undefined && loadedUsers.length > 0) {
      this.journalUsersPreviews = loadedUsers;
      this.usersListLoaded = true;
    }
  }

  public get journalListObservable(): Subject<JournalListEvent> {
    return this._journalListObservable;
  }

  public get dummiesIndexes(): ReadonlyArray<number> {
    return JournalsListComponent.DUMMIES_INDEXES;
  }
}
