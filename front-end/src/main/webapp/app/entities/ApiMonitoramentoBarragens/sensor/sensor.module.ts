import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FrontEndSharedModule } from 'app/shared/shared.module';
import { SensorComponent } from './sensor.component';
import { SensorDetailComponent } from './sensor-detail.component';
import { SensorUpdateComponent } from './sensor-update.component';
import { SensorDeleteDialogComponent } from './sensor-delete-dialog.component';
import { sensorRoute } from './sensor.route';

@NgModule({
  imports: [FrontEndSharedModule, RouterModule.forChild(sensorRoute)],
  declarations: [SensorComponent, SensorDetailComponent, SensorUpdateComponent, SensorDeleteDialogComponent],
  entryComponents: [SensorDeleteDialogComponent]
})
export class ApiMonitoramentoBarragensSensorModule {}
