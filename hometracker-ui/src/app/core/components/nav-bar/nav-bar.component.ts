import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  
  modules: Promise<HTModule[]>;
  
  private _mobileQueryListener: () => void;
  
  constructor(changeDetectorRef: ChangeDetectorRef, media: MediaMatcher, private moduleService: ModulesService, private router: Router, private activeRoute: ActivatedRoute) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addEventListener('change', this._mobileQueryListener);
  }
  ngOnInit(): void {
    this.modules = this.moduleService.getModules().toPromise();
  }
  
  ngOnDestroy(): void {
    this.mobileQuery.removeEventListener('change', this._mobileQueryListener);
  }

  redirectTo(module: HTModule) {
    this.router.navigateByUrl("/", {skipLocationChange: true})
      .then(() => this.router.navigate(['modules', module.name, 'info']));
  }
}
