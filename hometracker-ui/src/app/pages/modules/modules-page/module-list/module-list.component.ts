import { Component, Input } from '@angular/core';
import { HTModule } from 'src/app/core/models/htmodule.model';

@Component({
  selector: 'app-module-list',
  templateUrl: './module-list.component.html',
  styleUrls: ['./module-list.component.scss']
})
export class ModuleListComponent {

  @Input() public modules: HTModule[];

  

}
