import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { DetailsServiceService } from '../details-service.service';

@Component({
  selector: '[page=curriculum]',
  templateUrl: './curriculum.component.html',
  styleUrls: ['./curriculum.component.css']
})
export class CurriculumComponent implements OnInit {

  private static state = false;

  public hideCard = true;
  public dialogHidden = false;

  public dialogText = '';
  public tueCounter = 1;
  public weekCounter = 6;

  public extraElements?: boolean;

  constructor(private readonly detailsService: DetailsServiceService) { }

  ngOnInit(): void {
    this.extraElements = CurriculumComponent.state;
  }

  public openClicked(): void {
    this.detailsService?.detailsNotifier.next('open');
    setTimeout(_ => CurriculumComponent.state = true, 5000);
  }

  public onDialogConfirm(): void {
    this.hideCard = false;
    this.dialogHidden = true;
    this.dialogText = '';
    this.tueCounter = 2;
    this.weekCounter = 7;
    setTimeout(r => this.dialogHidden = false, 500);
  }
}
