import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncidente } from 'app/shared/model/ApiMonitoramentoBarragens/incidente.model';

@Component({
  selector: 'jhi-incidente-detail',
  templateUrl: './incidente-detail.component.html'
})
export class IncidenteDetailComponent implements OnInit {
  incidente: IIncidente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incidente }) => (this.incidente = incidente));
  }

  previousState(): void {
    window.history.back();
  }
}
