import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JournalPreview } from '../model/journals-list.model'
import { JournalUserPreview } from '../model/journals-list.model'

type JournalUserOnLoadCallback = (response: Array<JournalUserPreview>) => void;

@Injectable({
  providedIn: 'root'
})
export class JournalsListService {

  private readonly url = "http://localhost:8080/journals?requesterId=74148075-b59b-4ac4-860d-92a2dbf74abd";

  constructor(private httpClient: HttpClient) { }

  public loadAvailableJournals(requesterId: string, target: Array<JournalPreview>): void {
    this.httpClient.get<Array<JournalPreview>>(this.url)
        .subscribe(previews => target.push(...previews));
  }

  public loadJournalUsers(journalId: number | undefined,
                          callback: JournalUserOnLoadCallback): void {
    if (journalId !== undefined) {
      let url = this.createUrlForLoadingJournalUsers(journalId);
      this.httpClient.get<Array<JournalUserPreview>>(url)
          .subscribe(previews => callback(previews));
    }
  }

  public formatListItemIndex(listItemIndex: number | undefined): string {
    if (listItemIndex === undefined) {
      return '';
    }
    let indexStrngified = listItemIndex.toString();
    let digitsAmount = indexStrngified.length;
    if (digitsAmount === 1) {
      return `0${listItemIndex}`;
    }
    return indexStrngified;
  }

  private createUrlForLoadingJournalUsers(journalId: number): string {
    return 'http://localhost:8080/journals/' + journalId + '/users';
  }
}
