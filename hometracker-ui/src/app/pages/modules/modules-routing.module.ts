import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModuleInfoComponent } from './modules-page/module-info/module-info.component';
import { ModulesPageComponent } from './modules-page/modules-page.component';

const routes: Routes = [
    { path: '', component: ModulesPageComponent, pathMatch: 'full' },
    { path: ':module/info', component: ModuleInfoComponent}
];

@NgModule({
    imports: [ RouterModule.forChild(routes) ],
    exports: [ RouterModule ]
})
export class ModulesRoutingModule { }