import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleSearchComponent } from './module-search.component';

describe('ModuleSearchComponent', () => {
  let component: ModuleSearchComponent;
  let fixture: ComponentFixture<ModuleSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModuleSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModuleSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
