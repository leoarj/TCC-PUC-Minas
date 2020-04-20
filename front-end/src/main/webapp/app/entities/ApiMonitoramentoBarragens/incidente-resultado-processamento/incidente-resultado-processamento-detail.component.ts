import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncidenteResultadoProcessamento } from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';

@Component({
  selector: 'jhi-incidente-resultado-processamento-detail',
  templateUrl: './incidente-resultado-processamento-detail.component.html'
})
export class IncidenteResultadoProcessamentoDetailComponent implements OnInit {
  incidenteResultadoProcessamento: IIncidenteResultadoProcessamento | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ incidenteResultadoProcessamento }) => (this.incidenteResultadoProcessamento = incidenteResultadoProcessamento)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
