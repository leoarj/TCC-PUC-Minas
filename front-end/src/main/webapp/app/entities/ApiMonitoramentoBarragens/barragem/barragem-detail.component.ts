import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBarragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';

@Component({
  selector: 'jhi-barragem-detail',
  templateUrl: './barragem-detail.component.html'
})
export class BarragemDetailComponent implements OnInit {
  barragem: IBarragem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ barragem }) => (this.barragem = barragem));
  }

  previousState(): void {
    window.history.back();
  }
}
