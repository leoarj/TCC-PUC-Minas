import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { IncidenteResultadoProcessamentoComponent } from './incidente-resultado-processamento.component';
import { IncidenteResultadoProcessamentoDetailComponent } from './incidente-resultado-processamento-detail.component';
import { incidenteResultadoProcessamentoRoute } from './incidente-resultado-processamento.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(incidenteResultadoProcessamentoRoute)],
  declarations: [IncidenteResultadoProcessamentoComponent, IncidenteResultadoProcessamentoDetailComponent]
})
export class ApiMonitoramentoBarragensIncidenteResultadoProcessamentoModule {}
