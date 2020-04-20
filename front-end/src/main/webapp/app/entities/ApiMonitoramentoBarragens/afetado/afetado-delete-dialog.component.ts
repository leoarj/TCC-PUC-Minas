import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAfetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';
import { AfetadoService } from './afetado.service';

@Component({
  templateUrl: './afetado-delete-dialog.component.html'
})
export class AfetadoDeleteDialogComponent {
  afetado?: IAfetado;

  constructor(protected afetadoService: AfetadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.afetadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('afetadoListModification');
      this.activeModal.close();
    });
  }
}
