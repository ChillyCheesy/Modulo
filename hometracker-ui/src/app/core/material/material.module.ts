import { NgModule } from '@angular/core';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

const materials = [
  MatCardModule,
  MatButtonModule
];


@NgModule({
  imports: [ materials ],
  exports: [ materials ]
})
export class MaterialModule { }
