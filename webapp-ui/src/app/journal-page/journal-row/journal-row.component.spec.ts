import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalRowComponent } from './journal-row.component';

describe('JournalRowComponent', () => {
  let component: JournalRowComponent;
  let fixture: ComponentFixture<JournalRowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalRowComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
