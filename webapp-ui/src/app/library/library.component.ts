import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: '[page=library]',
  templateUrl: './library.component.html',
  styleUrls: ['./library.component.css']
})
export class LibraryComponent {

  private readonly url = 'http://localhost:8080/documents/lib/7816231823123423'

  public hideAddFileDialog = true;
  public hideAddedFile = true;

  public hideOnFav = false;
  public hideOnSearch = false;

  public btnActive1 = false;
  public btnActive2 = false;

  public dummy = '';

  constructor(private readonly httpClient: HttpClient) { }

  public onAddFileClicked(): void {
    this.hideAddFileDialog = false;
  }

  public onConfirmFileClicked(): void {
    this.hideAddFileDialog = true;
    setTimeout(_ => this.hideAddedFile = false, 800);
  }

  public onSetBtnActive(id: number): void {
    if (id === 1) {
      this.btnActive1 = !this.btnActive1;
    }
    if (id === 2) {
      this.btnActive2 = !this.btnActive2;
    }
  }

  public onFavClicked(): void {
    this.hideOnFav = !this.hideOnFav;
  }

  public onSearchChanged(): void {
    this.hideOnSearch = true;
    if (this.dummy.length === 0) {
      this.hideOnSearch = false;
    }
  }

  public onFileClicked(): void {
    this.httpClient.get(this.url, {
      headers: {
        "Accept": "application/pdf"
      },
      responseType: 'blob'
    }).subscribe(response => this.downloadFile(response))
  }

  private downloadFile(data: any) {
    console.log(data);
    const blob = new Blob([data], { type: 'application/pdf' });
    const url= window.URL.createObjectURL(blob);
    window.open(url);
  }
}
