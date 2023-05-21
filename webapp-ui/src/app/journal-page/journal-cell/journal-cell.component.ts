import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
  selector: '[element=journal-cell]',
  templateUrl: './journal-cell.component.html',
  styleUrls: ['./journal-cell.component.css']
})
export class JournalCellComponent implements OnInit {

  @Input() public mark?: number;
  @Input() public cellId?: string;
  @Input() public highlight?: string;
  @Input() public cellClickedParentObservable?: Subject<string>;

  @Output('cellClicked') clickedEventEmitter = new EventEmitter<string>(true);

  public focused: boolean = false;

  public ngOnInit(): void {
    this.cellClickedParentObservable?.subscribe((cellId: string) => this.resloveClickedState(cellId));
  }

  public onClick(): void {
    this.focused = !this.focused;
    this.clickedEventEmitter.emit(this.cellId);
  }

  private resloveClickedState(cellId: string): void {
    console.log(this.cellId === cellId);
    if (this.cellId !== cellId) {
      this.focused = false;
    }
  }
}
