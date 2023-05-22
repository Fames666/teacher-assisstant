import { NgModule, SecurityContext } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MarkdownModule } from 'ngx-markdown';
import { HttpClient, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavigationComponent } from './navigation/navigation.component';
import { CurriculumComponent } from './curriculum/curriculum.component';
import { BreadcrumbsComponent } from './breadcrumbs/breadcrumbs.component';
import { CardComponent } from './curriculum/card/card.component';
import { CardDetailsComponent } from './curriculum/card-details/card-details.component';
import { JournalsListItemComponent } from './journals-list/journals-list-item/journals-list-item.component';
import { JournalStudentsItemComponent } from './journals-list/journal-students-item/journal-students-item.component';
import { JournalsListComponent } from './journals-list/journals-list.component';

import { JournalsListService } from './journals-list/service/journals-list-service.service';
import { JournalsListItemLoadingDummyComponent } from './journals-list/journals-list-item-loading-dummy/journals-list-item-loading-dummy.component';
import { LibraryComponent } from './library/library.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { NotesPageComponent } from './notes-page/notes-page.component';
import { MessagesPageComponent } from './messages-page/messages-page.component';
import { JournalPageComponent } from './journal-page/journal-page.component';
import { JournalAsideRowComponent } from './journal-page/journal-aside-row/journal-aside-row.component';
import { JournalRowComponent } from './journal-page/journal-row/journal-row.component';
import { JournalDateBlockComponent } from './journal-page/journal-date-block/journal-date-block.component';
import { JournalCellComponent } from './journal-page/journal-cell/journal-cell.component';
import { JournalActionFormComponent } from './journal-page/journal-action-form/journal-action-form.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MessageBoxComponent } from './messages-page/message-box/message-box.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationComponent,
    CurriculumComponent,
    BreadcrumbsComponent,
    CardComponent,
    CardDetailsComponent,
    JournalsListComponent,
    JournalsListItemComponent,
    JournalStudentsItemComponent,
    JournalsListItemLoadingDummyComponent,
    LibraryComponent,
    SignInComponent,
    NotesPageComponent,
    MessagesPageComponent,
    JournalPageComponent,
    JournalAsideRowComponent,
    JournalRowComponent,
    JournalDateBlockComponent,
    JournalCellComponent,
    JournalActionFormComponent,
    MessageBoxComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: 'curriculum', component: CurriculumComponent },
      { path: 'journals-list', component: JournalsListComponent },
      { path: 'journal/:id', component: JournalPageComponent },
      { path: 'messages', component: MessagesPageComponent },
      { path: 'lib', component: LibraryComponent },
      { path: '', redirectTo: 'curriculum', pathMatch: 'full' }
    ])
  ],
  providers: [JournalsListService],
  bootstrap: [AppComponent]
})
export class AppModule { }
