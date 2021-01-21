import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { from, Observable, of } from 'rxjs';
import { HTModule } from '../models/htmodule.model';
import { environment } from '../../../environments/environment';
import { find, mergeMap } from 'rxjs/operators';

@Injectable()
export class ModulesService {

  constructor(private httpClient: HttpClient) { }

  public getModules(): Observable<HTModule[]> {
    if(environment.production) {
      return this.httpClient.get<HTModule[]>('/HomeTracker/modules');
    } else {
      return of([{"name":"Server","version":"1.0.0","authors":["Owle"],"dependencies":[],"softDependencies":[],"main":null,"mainPageName":"index"},{"name":"HomeTracker","version":"BINKS-1.0.0","authors":["Owl-e"],"dependencies":[],"softDependencies":[],"main":"fr.owle.hometracker.HTAPI","mainPageName":"ui"},{"name":"Hello","version":"1.0.0","authors":["Owl-e"],"dependencies":[],"softDependencies":[],"main":"fr.owle.hello.HelloModule","mainPageName":"hello"}]);
    }
  }

  public getModuleByName(name: string): Observable<HTModule> {
    if(environment.production) {
      return this.httpClient.get<HTModule>('/HomeTracker/modules/' + name);
    } else {
      return this.getModules().pipe(
        mergeMap(modules => from(modules)),
        find(module => module.name == name)
      );
    }
  }

  public getPagesModules(module: HTModule): Observable<string[]> {
    if(environment.production) {
      return this.httpClient.get<string[]>('/HomeTracker/modules/' + module.name + '/pages');
    } else {
      return of(['page1','page2']);
    }
  }
}
