import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { JournalsListService } from 'src/app/journals-list/service/journals-list-service.service';
import { JournalsListModuleConstants } from 'src/app/journals-list/common/journals-list.common';
import { JournalListEvent } from 'src/app/journals-list/common/journals-list.common';
import { JournalUserPreview } from 'src/app/journals-list/model/journals-list.model';

@Component({
  selector: '[element=journals-list-item]',
  templateUrl: './journals-list-item.component.html',
  styleUrls: ['./journals-list-item.component.css']
})
export class JournalsListItemComponent implements OnInit {

  @Input() id?: number;
  @Input() journalFullName?: string;
  @Input() listItemIndex?: number;
  @Input() parentObservable?: Subject<JournalListEvent>;

  @Output('journalSelected') clickedEventEmitter = new EventEmitter<number>(true);
  @Output('usersLoaded') usersLoadedEventEmitter = new EventEmitter<Array<JournalUserPreview>>();

  private _clicked: boolean;
  private _service: JournalsListService;

  constructor(service: JournalsListService) {
    this._clicked = false;
    this._service = service;
  }

  public ngOnInit(): void {
    this.parentObservable?.subscribe(parentEvent => this.processParentEvent(parentEvent));
  }

  public onClick(): void {
    this.clickedEventEmitter.emit(this.id);
    if (this._clicked) {
      return;
    }
    this._service.loadJournalUsers(this.id, loadedUsers => {
      this.usersLoadedEventEmitter.emit(loadedUsers);
    });
  }

  public formatListItemIndex(): string {
    return this._service.formatListItemIndex(this.listItemIndex);
  }

  private processParentEvent(parentEvent: JournalListEvent): void {
    if (parentEvent.eventCode === JournalsListModuleConstants.UPDATE_CLICKED_STATE_EVENT_CODE) {
      this._clicked = this.id === parentEvent.journalId;
    }
  }

  public get clicked(): boolean {
    return this._clicked;
  }
}
