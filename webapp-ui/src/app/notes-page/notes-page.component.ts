import { Component } from '@angular/core';

@Component({
  selector: '[page=notes]',
  templateUrl: './notes-page.component.html',
  styleUrls: ['./notes-page.component.css']
})
export class NotesPageComponent {

  public hideAddNoteWindow = true;
  public hideNewNote = true

  public onAddNoteClicked(): void {
    this.hideAddNoteWindow = false;
  }

  public onConfirmNoteClicked(): void {
    this.hideAddNoteWindow = true;
    this.hideNewNote = false;
  }
}
