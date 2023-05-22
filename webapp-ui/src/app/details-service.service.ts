import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DetailsServiceService {

  public detailsNotifier = new Subject<string>();

  constructor() { }
}
