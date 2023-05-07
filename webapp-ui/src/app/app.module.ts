import {NgModule, SecurityContext} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {MarkdownModule} from 'ngx-markdown';
import {HttpClient, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {NavigationComponent} from './navigation/navigation.component';
import {CurriculumComponent} from './curriculum/curriculum.component';
import {BreadcrumbsComponent} from './breadcrumbs/breadcrumbs.component';
import {CardComponent} from './curriculum/card/card.component';
import {CardDetailsComponent} from './curriculum/card-details/card-details.component';
import { JournalsListItemComponent } from './journals-list/journals-list-item/journals-list-item.component';
import { JournalStudentsItemComponent } from './journals-list/journal-students-item/journal-students-item.component';
import { JournalsListComponent } from './journals-list/journals-list.component';

import { JournalsListService } from './service/journals-list-service.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationComponent,
    CurriculumComponent,
    BreadcrumbsComponent,
    CardComponent,
    CardDetailsComponent,
    JournalsListItemComponent,
    JournalStudentsItemComponent,
    JournalsListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MarkdownModule.forRoot({ loader: HttpClient, sanitize: SecurityContext.NONE })
  ],
  providers: [JournalsListService],
  bootstrap: [AppComponent]
})
export class AppModule { }
