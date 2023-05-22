import { Component } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: '[page=messages]',
  templateUrl: './messages-page.component.html',
  styleUrls: ['./messages-page.component.css']
})
export class MessagesPageComponent {

  public selectedElement: string = 'inbox';
  public sentItemsAmount: number = 0;
  public hideHiddentContact = true;
  public hideHiddentContactAdd = true;
  public hideNewMessageForm = true;
  public changeBoxSubject = new Subject<string>();

  public onAsideItemClick(type: string): void {
    this.selectedElement = type;
    if (type === 'inbox') {
      this.changeBoxSubject.next('inbox');
    }
    if (type === 'sent') {
      this.changeBoxSubject.next('sent');
    }
  }

  public onAddContactClick(): void {
    this.hideHiddentContactAdd = false;
  }

  public onContactConfirmed(): void {
    this.hideHiddentContactAdd = true;
    this.hideHiddentContact = false;
  }

  public onCreateMessageClick(): void {
    this.hideNewMessageForm = false;
  }

  public onSendMessageClick(): void {
    this.hideNewMessageForm = true;
    this.changeBoxSubject.next('sent');
    this.sentItemsAmount = 1;
    this.selectedElement = 'sent';
  }
}
