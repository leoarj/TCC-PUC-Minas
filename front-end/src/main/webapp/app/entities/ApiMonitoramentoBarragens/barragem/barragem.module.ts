import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { BarragemComponent } from './barragem.component';
import { BarragemDetailComponent } from './barragem-detail.component';
import { BarragemUpdateComponent } from './barragem-update.component';
import { BarragemDeleteDialogComponent } from './barragem-delete-dialog.component';
import { barragemRoute } from './barragem.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(barragemRoute)],
  declarations: [BarragemComponent, BarragemDetailComponent, BarragemUpdateComponent, BarragemDeleteDialogComponent],
  entryComponents: [BarragemDeleteDialogComponent]
})
export class ApiMonitoramentoBarragensBarragemModule {}
