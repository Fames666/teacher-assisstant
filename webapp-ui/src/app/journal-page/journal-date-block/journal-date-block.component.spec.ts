import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalDateBlockComponent } from './journal-date-block.component';

describe('JournalDateBlockComponent', () => {
  let component: JournalDateBlockComponent;
  let fixture: ComponentFixture<JournalDateBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalDateBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalDateBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
