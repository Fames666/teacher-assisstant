import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DetailsServiceService } from './details-service.service';

@Component({
  selector: 'body',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'webapp-ui';
  showDetails = false;
  showLogin = true;

  constructor(private readonly detailsService: DetailsServiceService,
              private readonly router: Router) { }

  ngOnInit(): void {
    this.detailsService?.detailsNotifier.subscribe(event => this.onOpen());
    this.detailsService?.loginNotifier.subscribe(event => this.onOpenLogin());
  }

  onClose() {
    this.showDetails = false;
    this.router.navigate(['curriculum']);
  }

  onOpen() {
    this.showDetails = true;
  }

  onCloseLogin(): void {
    this.showLogin = false;
    this.router.navigate(['/curriculum'])
  }

  onOpenLogin(): void {
    this.showLogin = true;
  }
}
