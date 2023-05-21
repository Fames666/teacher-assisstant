import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: '[element=journal-action-form]',
  templateUrl: './journal-action-form.component.html',
  styleUrls: ['./journal-action-form.component.css']
})
export class JournalActionFormComponent {

  public mark?: number;

  @Output("markConfirmed") public markConfirmed = new EventEmitter<number>(true);

  public onConfirm() {
    this.markConfirmed.emit(this.mark);
  }
}
