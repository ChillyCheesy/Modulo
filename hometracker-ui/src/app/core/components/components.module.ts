import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MaterialModule } from '../material/material.module';
import { RouterModule } from '@angular/router';
import { ModuleSearchComponent } from './module-search/module-search.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { ModulesTreeComponent } from './modules-tree/modules-tree.component';



@NgModule({
  declarations: [NavBarComponent, ModuleSearchComponent, ModulesTreeComponent],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [
    NavBarComponent,
    ModuleSearchComponent,
    ModulesTreeComponent
  ]
})
export class ComponentsModule { }
