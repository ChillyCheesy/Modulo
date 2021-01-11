import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModulesRoutingModule } from './modules-routing.module';
import { CoreModule } from 'src/app/core/core.module';

import { ModulesPageComponent } from './modules-page/modules-page.component';
import { ModuleListComponent } from './modules-page/module-list/module-list.component';
import { ModuleItemComponent } from './modules-page/module-item/module-item.component';
import { MaterialModule } from 'src/app/core/material/material.module';
import { ModuleInfoComponent } from './modules-page/module-info/module-info.component';

@NgModule({
  declarations: [
    ModulesPageComponent,
    ModuleListComponent,
    ModuleItemComponent,
    ModuleInfoComponent
  ],
  imports: [
    CommonModule,
    CoreModule,
    MaterialModule,
    ModulesRoutingModule
  ]
})
export class ModulesModule { }
