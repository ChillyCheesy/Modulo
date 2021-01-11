import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { ModulesService } from './services/modules.service';
import { RedirectService } from './services/redirect.service';

@NgModule({
  imports: [ HttpClientModule ],
  providers: [ ModulesService, RedirectService ]
})
export class CoreModule { }
