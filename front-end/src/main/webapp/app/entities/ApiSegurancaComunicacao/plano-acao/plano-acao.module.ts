import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { PlanoAcaoComponent } from './plano-acao.component';
import { PlanoAcaoDetailComponent } from './plano-acao-detail.component';
import { PlanoAcaoUpdateComponent } from './plano-acao-update.component';
import { PlanoAcaoDeleteDialogComponent } from './plano-acao-delete-dialog.component';
import { planoAcaoRoute } from './plano-acao.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(planoAcaoRoute)],
  declarations: [PlanoAcaoComponent, PlanoAcaoDetailComponent, PlanoAcaoUpdateComponent, PlanoAcaoDeleteDialogComponent],
  entryComponents: [PlanoAcaoDeleteDialogComponent]
})
export class ApiSegurancaComunicacaoPlanoAcaoModule {}
