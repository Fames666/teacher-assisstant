import { Component } from '@angular/core';

@Component({
  selector: '[element=journals-list-item-loading-dummy]',
  templateUrl: './journals-list-item-loading-dummy.component.html',
  styleUrls: [
    '../journals-list-item/journals-list-item.component.css',
    './journals-list-item-loading-dummy.component.css'
  ]
})
export class JournalsListItemLoadingDummyComponent {

  private static placeholderWithCharsLength_45: string = 'x'.repeat(45);
  private static placeholderWithCharsLength_15: string = 'x'.repeat(15);

  public get placeholder_45(): string {
    return JournalsListItemLoadingDummyComponent.placeholderWithCharsLength_45;
  }

  public get placeholder_15(): string {
    return JournalsListItemLoadingDummyComponent.placeholderWithCharsLength_15;
  }
}
