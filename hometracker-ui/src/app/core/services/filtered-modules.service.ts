import { Injectable } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { HTModule } from '../models/htmodule.model';
import { ModulesService } from './modules.service';

@Injectable()
export class FilteredModulesService {
    
    constructor(private moduleService: ModulesService) {}
    
    public formControl: FormControl
    
    public getFilteredModules(): Observable<HTModule[]> {
        return this.formControl.valueChanges.pipe(
            mergeMap(filter =>
                this.moduleService.getModules().pipe(
                    map(modules => 
                        modules.filter(module => 
                            module.name.toLowerCase().indexOf(filter) === 0)
        ))));
    }
}
                    