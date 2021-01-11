import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { HTModule } from 'src/app/core/models/htmodule.model';
import { ModulesService } from 'src/app/core/services/modules.service';

@Component({
  selector: 'app-modules-page',
  templateUrl: './modules-page.component.html',
  styleUrls: ['./modules-page.component.scss']
})
export class ModulesPageComponent implements OnInit {

  public modules: Observable<HTModule[]>;

  constructor(private modulesService: ModulesService) { }

  public ngOnInit(): void {
    this.modules = this.modulesService.getModules();
  }

}
