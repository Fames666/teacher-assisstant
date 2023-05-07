import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: '[element=journal-students-item]',
  templateUrl: './journal-students-item.component.html',
  styleUrls: ['./journal-students-item.component.css']
})
export class JournalStudentsItemComponent {

  @Input() public id?: string;
  @Input() public fullName?: string;
  @Input() public lastOnline?: string;
  @Input() public marker?: string;
  @Input() public remarksAmount?: number;
}
