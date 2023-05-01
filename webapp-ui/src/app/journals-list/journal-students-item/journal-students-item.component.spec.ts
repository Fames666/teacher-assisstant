import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalStudentsItemComponent } from './journal-students-item.component';

describe('JournalStudentsItemComponent', () => {
  let component: JournalStudentsItemComponent;
  let fixture: ComponentFixture<JournalStudentsItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalStudentsItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalStudentsItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
