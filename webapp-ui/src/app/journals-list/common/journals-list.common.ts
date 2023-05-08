import { EventEmitter } from "@angular/core";
import { JournalUserPreview } from "src/app/journals-list/model/journals-list.model";

export class JournalsListModuleConstants {

  public static readonly UPDATE_CLICKED_STATE_EVENT_CODE = "event.update.clicked.state";
}

export class JournalListEvent {

  constructor(public readonly eventCode: string,
              public readonly journalId?: number) { }
}
