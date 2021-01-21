import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulesTreeComponent } from './modules-tree.component';

describe('ModulesTreeComponent', () => {
  let component: ModulesTreeComponent;
  let fixture: ComponentFixture<ModulesTreeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModulesTreeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModulesTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
