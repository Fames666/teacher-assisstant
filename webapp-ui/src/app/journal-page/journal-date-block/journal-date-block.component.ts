import { Component, Input } from '@angular/core';
import { DayOfWeek } from '../model/journal-page.models';
import { KeyValue } from '@angular/common';

@Component({
  selector: '[element=journal-date-block]',
  templateUrl: './journal-date-block.component.html',
  styleUrls: ['./journal-date-block.component.css']
})
export class JournalDateBlockComponent {

  @Input() public month?: string;
  @Input() public daysOfWeek?: DayOfWeek[];

  public originalOrder = (__: any, _: any): number => {
    return 0;
  }
}
