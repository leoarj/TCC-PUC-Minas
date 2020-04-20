import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { EventoMedicaoClassificacaoAlertaComponent } from './evento-medicao-classificacao-alerta.component';
import { EventoMedicaoClassificacaoAlertaDetailComponent } from './evento-medicao-classificacao-alerta-detail.component';
import { EventoMedicaoClassificacaoAlertaUpdateComponent } from './evento-medicao-classificacao-alerta-update.component';
import { EventoMedicaoClassificacaoAlertaDeleteDialogComponent } from './evento-medicao-classificacao-alerta-delete-dialog.component';
import { eventoMedicaoClassificacaoAlertaRoute } from './evento-medicao-classificacao-alerta.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(eventoMedicaoClassificacaoAlertaRoute)],
  declarations: [
    EventoMedicaoClassificacaoAlertaComponent,
    EventoMedicaoClassificacaoAlertaDetailComponent,
    EventoMedicaoClassificacaoAlertaUpdateComponent,
    EventoMedicaoClassificacaoAlertaDeleteDialogComponent
  ],
  entryComponents: [EventoMedicaoClassificacaoAlertaDeleteDialogComponent]
})
export class ApiMonitoramentoBarragensEventoMedicaoClassificacaoAlertaModule {}
