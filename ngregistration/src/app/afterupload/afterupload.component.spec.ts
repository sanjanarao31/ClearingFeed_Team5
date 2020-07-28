import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AfteruploadComponent } from './afterupload.component';

describe('AfteruploadComponent', () => {
  let component: AfteruploadComponent;
  let fixture: ComponentFixture<AfteruploadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AfteruploadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AfteruploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
