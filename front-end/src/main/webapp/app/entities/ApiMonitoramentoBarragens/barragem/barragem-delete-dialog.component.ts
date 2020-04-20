import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBarragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';
import { BarragemService } from './barragem.service';

@Component({
  templateUrl: './barragem-delete-dialog.component.html'
})
export class BarragemDeleteDialogComponent {
  barragem?: IBarragem;

  constructor(protected barragemService: BarragemService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.barragemService.delete(id).subscribe(() => {
      this.eventManager.broadcast('barragemListModification');
      this.activeModal.close();
    });
  }
}
