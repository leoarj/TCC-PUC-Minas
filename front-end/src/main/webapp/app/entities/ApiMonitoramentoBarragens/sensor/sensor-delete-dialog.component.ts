import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISensor } from 'app/shared/model/ApiMonitoramentoBarragens/sensor.model';
import { SensorService } from './sensor.service';

@Component({
  templateUrl: './sensor-delete-dialog.component.html'
})
export class SensorDeleteDialogComponent {
  sensor?: ISensor;

  constructor(protected sensorService: SensorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sensorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sensorListModification');
      this.activeModal.close();
    });
  }
}
