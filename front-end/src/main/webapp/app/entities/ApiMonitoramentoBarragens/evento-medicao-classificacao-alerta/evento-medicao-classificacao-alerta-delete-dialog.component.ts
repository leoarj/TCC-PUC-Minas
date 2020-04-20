import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';
import { EventoMedicaoClassificacaoAlertaService } from './evento-medicao-classificacao-alerta.service';

@Component({
  templateUrl: './evento-medicao-classificacao-alerta-delete-dialog.component.html'
})
export class EventoMedicaoClassificacaoAlertaDeleteDialogComponent {
  eventoMedicaoClassificacaoAlerta?: IEventoMedicaoClassificacaoAlerta;

  constructor(
    protected eventoMedicaoClassificacaoAlertaService: EventoMedicaoClassificacaoAlertaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventoMedicaoClassificacaoAlertaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventoMedicaoClassificacaoAlertaListModification');
      this.activeModal.close();
    });
  }
}
