import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalsListItemComponent } from './journals-list-item.component';

describe('JournalsListItemComponent', () => {
  let component: JournalsListItemComponent;
  let fixture: ComponentFixture<JournalsListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalsListItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalsListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
