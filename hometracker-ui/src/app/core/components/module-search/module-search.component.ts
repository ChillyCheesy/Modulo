import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, of, Subscription } from 'rxjs';
import { HTModule } from '../../models/htmodule.model';
import { ModulesService } from '../../services/modules.service';
import { Router } from '@angular/router';
import { RedirectService } from '../../services/redirect.service';

@Component({
  selector: 'app-module-search',
  templateUrl: './module-search.component.html',
  styleUrls: ['./module-search.component.scss']
})
export class ModuleSearchComponent implements OnInit {

  modules: Observable<HTModule[]>;
  formControl: FormControl = new FormControl();

  constructor(private moduleService: ModulesService, private redirectService: RedirectService, private router: Router) { }

  ngOnInit(): void {
    this.modules = this.moduleService.getModules();
    this.moduleService.filterModules(this.formControl);
  }
}