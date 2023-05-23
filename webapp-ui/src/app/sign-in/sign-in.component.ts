import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: '[page=sign-in]',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {

  @Output("onCloseLogin") public eventEmitter = new EventEmitter<string>(true);

  onCloseLogin(): void {
    setTimeout(_ => this.eventEmitter.emit('close'), 800);
  }
}
