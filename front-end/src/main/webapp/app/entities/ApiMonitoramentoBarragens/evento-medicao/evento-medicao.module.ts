import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { EventoMedicaoComponent } from './evento-medicao.component';
import { EventoMedicaoDetailComponent } from './evento-medicao-detail.component';
import { eventoMedicaoRoute } from './evento-medicao.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(eventoMedicaoRoute)],
  declarations: [EventoMedicaoComponent, EventoMedicaoDetailComponent]
})
export class ApiMonitoramentoBarragensEventoMedicaoModule {}
