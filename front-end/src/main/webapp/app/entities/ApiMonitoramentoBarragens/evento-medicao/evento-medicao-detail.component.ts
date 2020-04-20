import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventoMedicao } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao.model';

@Component({
  selector: 'jhi-evento-medicao-detail',
  templateUrl: './evento-medicao-detail.component.html'
})
export class EventoMedicaoDetailComponent implements OnInit {
  eventoMedicao: IEventoMedicao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventoMedicao }) => (this.eventoMedicao = eventoMedicao));
  }

  previousState(): void {
    window.history.back();
  }
}
