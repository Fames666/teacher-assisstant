import { Component } from '@angular/core';
import { Input } from '@angular/core';

@Component({
  selector: '[element=journal-students-item]',
  templateUrl: './journal-students-item.component.html',
  styleUrls: ['./journal-students-item.component.css']
})
export class JournalStudentsItemComponent {

  // TODO: replace with value from BE
  private static readonly ONLINE_STATUS = 'Онлайн';

  @Input() public id?: string;
  @Input() public fullName?: string;
  @Input() public lastOnline?: string;
  @Input() public marker?: string;
  @Input() public remarksAmount?: number;

  public resolveMarkerType(): string {
    if (this.remarksAmount === 0) {
      return this.lastOnline === JournalStudentsItemComponent.ONLINE_STATUS
          ? 'online' : 'offline';
    }
    return 'remark';
  }
}
