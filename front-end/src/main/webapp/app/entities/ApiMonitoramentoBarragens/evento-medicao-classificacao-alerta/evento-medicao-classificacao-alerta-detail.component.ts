import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';

@Component({
  selector: 'jhi-evento-medicao-classificacao-alerta-detail',
  templateUrl: './evento-medicao-classificacao-alerta-detail.component.html'
})
export class EventoMedicaoClassificacaoAlertaDetailComponent implements OnInit {
  eventoMedicaoClassificacaoAlerta: IEventoMedicaoClassificacaoAlerta | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ eventoMedicaoClassificacaoAlerta }) => (this.eventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlerta)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
