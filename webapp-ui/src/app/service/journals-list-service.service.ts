import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JournalPreview } from '../model/journals-list.model'
import { JournalUserPreview } from '../model/journals-list.model'

@Injectable({
  providedIn: 'root'
})
export class JournalsListService {

  private readonly url = "http://localhost:8080/journals?requesterId=74148075-b59b-4ac4-860d-92a2dbf74abd";
  private readonly url2 = "http://localhost:8080/journals/2487/users";

  constructor(private httpClient: HttpClient) { }

  loadAvailableJournals(requesterId: string, target: Array<JournalPreview>): void {
    this.httpClient.get<Array<JournalPreview>>(this.url)
        .subscribe(previews => target.push(...previews));
  }

  loadJournalUsers(journalId: number, target: Array<JournalUserPreview>): void {
    this.httpClient.get<Array<JournalUserPreview>>(this.url2)
        .subscribe(previews => target.push(...previews));
  }
}
