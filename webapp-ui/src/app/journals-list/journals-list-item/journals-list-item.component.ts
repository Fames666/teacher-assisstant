import { Component, Input } from '@angular/core';

@Component({
  selector: '[element=journals-list-item]',
  templateUrl: './journals-list-item.component.html',
  styleUrls: ['./journals-list-item.component.css']
})
export class JournalsListItemComponent {

  @Input() public id?: number;
  @Input() public journalFullName?: string;
  @Input() public listItemIndex?: number;

  // TODO: move to service
  public listItemIndexFormatted(): string {
    if (this.listItemIndex === undefined) {
      return '';
    }
    let indexStrngified = this.listItemIndex.toString();
    let digitsAmount = indexStrngified.length;
    if (digitsAmount === 1) {
      return `0${this.listItemIndex}`;
    }
    return indexStrngified;
  }
}
