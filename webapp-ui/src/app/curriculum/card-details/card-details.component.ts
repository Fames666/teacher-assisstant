import { Component, EventEmitter, Output } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: '[window=card-details]',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css']
})
export class CardDetailsComponent {

  public hideDescription = true;
  public hideDescriptionContent = false;
  public hideDescriptionText = true;

  public hideFiles = true;
  public hideFilesSelection = false;
  public hideFilesContent = true;
  public hideKaterina = true;
  public hideDemid = true;
  public attSelect = '';

  public hideLinks = true;
  public hideLinksInput = false;
  public hideLinksContent = true;

  public hideTodo = true;
  public hideTodoInput = false;
  public hideTodoContent = true;

  public hideComment = true;
  public commentText = '';

  public hideTags = true;
  public hideTagsInput = false;
  public hideTagsContent = true;

  public hidePartic = true;
  public hideParticInput = false;
  public hideParticContent = true;

  public hideOptions = true;

  public selectedValue = '';

  @Output("onClose") public eventEmitter = new EventEmitter<string>(true);

  public closeBtnClick(): void {
    this.eventEmitter.emit('close');
  }

  public onAddBtnClicked(): void {
    this.hideOptions = false;
  }

  public onSelect(): void {
    if (this.selectedValue === 'tags') {
      this.hideTags = false;
    }
    if (this.selectedValue === 'part') {
      this.hidePartic = false;
    }
    if (this.selectedValue === 'desc') {
      this.hideDescription = false;
    }
    if (this.selectedValue === 'files') {
      this.hideFiles = false;
    }
    if (this.selectedValue === 'links') {
      this.hideLinks = false;
    }
    if (this.selectedValue === 'todo') {
      this.hideTodo = false;
    }
    this.hideOptions = true;
    this.selectedValue = '';
  }

  public onDescConfirm(): void {
    this.hideDescriptionContent = true;
    this.hideDescriptionText = false;
  }

  public onAttSelect(): void {
    if (this.attSelect == 'kat') {
      this.hideKaterina = false;
    }
    if (this.attSelect == 'dem') {
      this.hideDemid = false;
    }
    this.attSelect = '';
  }

  public onFilesConfirm(): void {
    this.hideFilesSelection = true;
    this.hideFilesContent = false;
  }

  public onLinksConfirm(): void {
    this.hideLinksInput = true;
    this.hideLinksContent = false;
  }

  public onTodoConfirm(): void {
    this.hideTodoInput = true;
    this.hideTodoContent = false;
  }

  public onCommentConfirm(): void {
    this.hideComment = false;
    this.commentText = '';
  }

  public onTagConfirm(): void {
    this.hideTagsInput = true;
    this.hideTagsContent = false;
  }

  public onPartConfirm(): void {
    this.hideParticInput = true;
    setTimeout(_ => this.hideParticContent = false, 700);
  }
}
