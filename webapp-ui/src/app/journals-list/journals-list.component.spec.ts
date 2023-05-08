import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalsListComponent } from './journals-list.component';

describe('JournalsListComponent', () => {
  let component: JournalsListComponent;
  let fixture: ComponentFixture<JournalsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
