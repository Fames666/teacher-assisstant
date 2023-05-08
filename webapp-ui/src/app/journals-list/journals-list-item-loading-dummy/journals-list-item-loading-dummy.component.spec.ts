import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JournalsListItemLoadingDummyComponent } from './journals-list-item-loading-dummy.component';

describe('JournalsListItemLoadingDummyComponent', () => {
  let component: JournalsListItemLoadingDummyComponent;
  let fixture: ComponentFixture<JournalsListItemLoadingDummyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JournalsListItemLoadingDummyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JournalsListItemLoadingDummyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
