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

  constructor(private readonly detailsService: DetailsServiceService,
              private readonly router: Router) { }

  ngOnInit(): void {
    this.detailsService?.detailsNotifier.subscribe(event => this.onOpen());
  }

  onClose() {
    this.showDetails = false;
    this.router.navigate(['curriculum']);
  }

  onOpen() {
    this.showDetails = true;
  }
}
