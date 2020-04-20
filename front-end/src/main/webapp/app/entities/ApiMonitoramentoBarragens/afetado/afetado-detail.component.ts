import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAfetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';

@Component({
  selector: 'jhi-afetado-detail',
  templateUrl: './afetado-detail.component.html'
})
export class AfetadoDetailComponent implements OnInit {
  afetado: IAfetado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afetado }) => (this.afetado = afetado));
  }

  previousState(): void {
    window.history.back();
  }
}
