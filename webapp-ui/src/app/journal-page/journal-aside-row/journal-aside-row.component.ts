import { Component, Input } from '@angular/core';

@Component({
  selector: '[element=journal-aside-row]',
  templateUrl: './journal-aside-row.component.html',
  styleUrls: ['../journal-page.component.css']
})
export class JournalAsideRowComponent {

  @Input() public fullName?: string;
  @Input() public progressLevel?: number;
  @Input() public averageMark?: number;
  @Input() public remarksAmount?: number;
}
