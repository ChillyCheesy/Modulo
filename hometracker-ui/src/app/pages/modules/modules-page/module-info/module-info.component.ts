import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLinkActive } from '@angular/router';
import { Observable } from 'rxjs';
import { HTModule } from 'src/app/core/models/htmodule.model';
import { ModulesService } from 'src/app/core/services/modules.service';
import { RedirectService } from 'src/app/core/services/redirect.service';

@Component({
  selector: 'app-module-info',
  templateUrl: './module-info.component.html',
  styleUrls: ['./module-info.component.scss']
})
export class ModuleInfoComponent implements OnInit {

  module: HTModule;
  modulePages: Observable<string[]>;

  constructor(
    private modulesService: ModulesService,
    private redirect: RedirectService,
    private router: ActivatedRoute) { }

  async ngOnInit(): Promise<void> {
    const moduleName = this.router.snapshot.paramMap.get('module');
    this.module = await this.modulesService.getModuleByName(moduleName).toPromise();
    this.modulePages = this.modulesService.getPagesModules(this.module);
  }

  public open(module: HTModule, page: string): void {
    this.redirect.navigateToModule(module, page);
  }

}
