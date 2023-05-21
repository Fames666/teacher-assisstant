import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalActionFormComponent } from './journal-action-form.component';

describe('JournalActionFormComponent', () => {
  let component: JournalActionFormComponent;
  let fixture: ComponentFixture<JournalActionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalActionFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalActionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
