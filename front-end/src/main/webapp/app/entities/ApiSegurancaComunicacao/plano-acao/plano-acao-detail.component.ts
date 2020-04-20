import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';

@Component({
  selector: 'jhi-plano-acao-detail',
  templateUrl: './plano-acao-detail.component.html'
})
export class PlanoAcaoDetailComponent implements OnInit {
  planoAcao: IPlanoAcao | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAcao }) => (this.planoAcao = planoAcao));
  }

  previousState(): void {
    window.history.back();
  }
}
