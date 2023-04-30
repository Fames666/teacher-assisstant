import { Component } from '@angular/core';

@Component({
  selector: '[page=journal]',
  templateUrl: './journal.component.html',
  styleUrls: ['./journal.component.css']
})
export class JournalComponent {

  journalsListHeader: string = "Список доступных журналов";
  journalsListHeaderHint: string = "(перетаскивайте для сортировки)";
}
