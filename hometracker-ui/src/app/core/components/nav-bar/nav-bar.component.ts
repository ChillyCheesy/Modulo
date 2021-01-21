import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnChanges, OnInit, SimpleChange } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HTModule } from '../../models/htmodule.model';
import { ModulesService } from '../../services/modules.service';
import { RedirectService } from '../../services/redirect.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  
  mobileQuery: MediaQueryList;
  modules: HTModule[];
  
  private _mobileQueryListener: () => void;
  
  constructor
  (
    private changeDetectorRef: ChangeDetectorRef,
    private media: MediaMatcher,
    private moduleService: ModulesService,
    private redirectService: RedirectService
    ) {
      this.mobileQuery = media.matchMedia('(max-width: 600px)');
      this._mobileQueryListener = () => changeDetectorRef.detectChanges();
      this.mobileQuery.addEventListener('change', this._mobileQueryListener);
    }
    async ngOnInit(): Promise<void> {
      this.moduleService.getModules().subscribe(modules => this.modules = modules).unsubscribe();
    }
    
    ngOnDestroy(): void {
      this.mobileQuery.removeEventListener('change', this._mobileQueryListener);
    }
    
    redirectTo(module: HTModule) {
      this.redirectService.navigateToInfoModule(module);
    }
    
    redirectToPage(module: HTModule, page: string) {
      this.redirectService.navigateToModule(module, page);
    }

    getPagesModule(module: HTModule): Observable<string[]> {
      return this.moduleService.getPagesModules(module);
    }
    
    receiveModules(modules: HTModule[]) {
      this.modules = modules;
    }
  }
  