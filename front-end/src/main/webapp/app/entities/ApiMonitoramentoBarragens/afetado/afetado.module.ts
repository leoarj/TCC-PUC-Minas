import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { AfetadoComponent } from './afetado.component';
import { AfetadoDetailComponent } from './afetado-detail.component';
import { AfetadoUpdateComponent } from './afetado-update.component';
import { AfetadoDeleteDialogComponent } from './afetado-delete-dialog.component';
import { afetadoRoute } from './afetado.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(afetadoRoute)],
  declarations: [AfetadoComponent, AfetadoDetailComponent, AfetadoUpdateComponent, AfetadoDeleteDialogComponent],
  entryComponents: [AfetadoDeleteDialogComponent]
})
export class ApiMonitoramentoBarragensAfetadoModule {}
