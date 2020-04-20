import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { IncidenteComponent } from './incidente.component';
import { IncidenteDetailComponent } from './incidente-detail.component';
import { incidenteRoute } from './incidente.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(incidenteRoute)],
  declarations: [IncidenteComponent, IncidenteDetailComponent]
})
export class ApiMonitoramentoBarragensIncidenteModule {}
