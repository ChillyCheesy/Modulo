import { Injectable } from '@angular/core';
import { HTModule } from '../models/htmodule.model';

@Injectable()
export class RedirectService {

  constructor() { }

  public navigateToModule(module: HTModule, page: String = module.mainPageName): void{
    const moduleName: string = module.name;
    const protocol: string = window.location.protocol;
    const host: string = window.location.host;
    const href = `${protocol}//${host}/${moduleName}/${page}`;
    window.location.href = href;
  }
}
