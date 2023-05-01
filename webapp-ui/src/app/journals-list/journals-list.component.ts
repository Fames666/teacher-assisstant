import { Component } from '@angular/core';

@Component({
  selector: '[page=journal]',
  templateUrl: './journals-list.component.html',
  styleUrls: ['./journals-list.component.css']
})
export class JournalsListComponent {

  journalsListHeader: string = "Список доступных журналов";
  journalsListHeaderHint: string = "(перетаскивайте для сортировки)";
}
