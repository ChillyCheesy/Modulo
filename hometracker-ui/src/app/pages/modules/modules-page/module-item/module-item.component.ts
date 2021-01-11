import { Component, Input } from '@angular/core';
import { HTModule } from 'src/app/core/models/htmodule.model';

@Component({
  selector: 'app-module-item',
  templateUrl: './module-item.component.html',
  styleUrls: ['./module-item.component.scss']
})
export class ModuleItemComponent {

  @Input() public module: HTModule;  
}
