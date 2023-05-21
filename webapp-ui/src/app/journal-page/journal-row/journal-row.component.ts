import { Component, EventEmitter, Input, Output } from '@angular/core';
import { JournalCell } from '../model/journal-page.models';
import { Subject } from 'rxjs';

@Component({
  selector: '[element=journal-row]',
  templateUrl: './journal-row.component.html',
  styleUrls: ['./journal-row.component.css']
})
export class JournalRowComponent {

  @Input() public studentId?: string;
  @Input() public journalCells?: JournalCell[];
  @Input() public cellClickedParentObservable?: Subject<string>;

  @Output('cellClicked') private clickedEventEmitter = new EventEmitter<string>(true);

  private activeCellId?: string;

  // Propagate to parent
  public onCellClicked(cellId: string): void {
    this.clickedEventEmitter.emit(cellId);
  }

  public calculateCellId(date: string): string {
    return this.studentId + ' ' + date;
  }
}
