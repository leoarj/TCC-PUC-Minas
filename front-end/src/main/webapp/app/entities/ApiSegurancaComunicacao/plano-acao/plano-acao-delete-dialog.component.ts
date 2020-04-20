import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';
import { PlanoAcaoService } from './plano-acao.service';

@Component({
  templateUrl: './plano-acao-delete-dialog.component.html'
})
export class PlanoAcaoDeleteDialogComponent {
  planoAcao?: IPlanoAcao;

  constructor(protected planoAcaoService: PlanoAcaoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoAcaoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('planoAcaoListModification');
      this.activeModal.close();
    });
  }
}
