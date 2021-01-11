import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HTModule } from '../models/htmodule.model';

@Injectable()
export class ModulesService {

  constructor(private httpClient: HttpClient) { }

  public getModules(): Observable<HTModule[]> {
    //return of([{"name":"Server","version":"1.0.0","authors":["Owle"],"dependencies":[],"softDependencies":[],"main":null,"mainPageName":"index"},{"name":"HomeTracker","version":"BINKS-1.0.0","authors":["Owl-e"],"dependencies":[],"softDependencies":[],"main":"fr.owle.hometracker.HTAPI","mainPageName":"ui"},{"name":"Hello","version":"1.0.0","authors":["Owl-e"],"dependencies":[],"softDependencies":[],"main":"fr.owle.hello.HelloModule","mainPageName":"hello"}]);
    return this.httpClient.get<HTModule[]>('/HomeTracker/modules');
  }

  public getModuleByName(name: string): Observable<HTModule> {
    return this.httpClient.get<HTModule>('/HomeTracker/modules/' + name);
  }

  public getPagesModules(module: HTModule): Observable<string[]> {
    return this.httpClient.get<string[]>('/HomeTracker/modules/' + module.name + '/pages');
  }

}
