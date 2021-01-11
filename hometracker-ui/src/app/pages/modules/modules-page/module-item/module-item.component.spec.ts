import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleItemComponent } from './module-item.component';

describe('ModuleItemComponent', () => {
  let component: ModuleItemComponent;
  let fixture: ComponentFixture<ModuleItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModuleItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModuleItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
