import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalAsideRowComponent } from './journal-aside-row.component';

describe('JournalAsideRowComponent', () => {
  let component: JournalAsideRowComponent;
  let fixture: ComponentFixture<JournalAsideRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalAsideRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalAsideRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
