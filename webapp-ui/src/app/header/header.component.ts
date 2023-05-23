import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { DetailsServiceService } from '../details-service.service';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  showExitBtn = false;

  constructor(private readonly router: Router,
              private readonly detailsService: DetailsServiceService) { }

  onLoginClicked() {
    this.showExitBtn = true;
  }

  onExitClicked() {
    setTimeout(_ => {
      this.showExitBtn = false;
      this.router.navigate(['/sign-in']);
      this.detailsService.loginNotifier.next('open');
    }, 800);
  }
}
