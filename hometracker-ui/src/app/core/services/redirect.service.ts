import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HTModule } from '../models/htmodule.model';

@Injectable()
export class RedirectService {

  constructor(private router: Router) { }

  public navigateToModule(module: HTModule, page: string = module.mainPageName): void{
    this.navigateToModuleByName(module.name, page);
  }

  public navigateToModuleByName(moduleName: string, page: string) {
    const protocol: string = window.location.protocol;
    const host: string = window.location.host;
    const href = `${protocol}//${host}/${moduleName}/${page}`;
    window.location.href = href;
  }

  public navigateToInfoModule(module: HTModule): void {
    this.navinavigateToInfoModuleByName(module.name);
  }
  
  public navinavigateToInfoModuleByName(moduleName: string): void {
    this.router.navigateByUrl("/", {skipLocationChange: true})
      .then(() => this.router.navigate(['modules', moduleName, 'info']));
  }

}
