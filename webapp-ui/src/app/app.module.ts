import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavigationComponent } from './navigation/navigation.component';
import { CurriculumComponent } from './curriculum/curriculum.component';
import { BreadcrumbsComponent } from './breadcrumbs/breadcrumbs.component';
import { CardComponent } from './curriculum/card/card.component';
import { CardDetailsComponent } from './curriculum/card-details/card-details.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationComponent,
    CurriculumComponent,
    BreadcrumbsComponent,
    CardComponent,
    CardDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
