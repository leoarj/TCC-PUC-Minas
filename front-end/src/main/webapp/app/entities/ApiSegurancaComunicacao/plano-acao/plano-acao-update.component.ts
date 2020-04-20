import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlanoAcao, PlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';
import { PlanoAcaoService } from './plano-acao.service';

@Component({
  selector: 'jhi-plano-acao-update',
  templateUrl: './plano-acao-update.component.html'
})
export class PlanoAcaoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [],
    descricao: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
    classificacao: [null, [Validators.required]],
    mensagemAlterta: [null, [Validators.required, Validators.maxLength(130)]]
  });

  constructor(protected planoAcaoService: PlanoAcaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoAcao }) => {
      this.updateForm(planoAcao);
    });
  }

  updateForm(planoAcao: IPlanoAcao): void {
    this.editForm.patchValue({
      id: planoAcao.id,
      tipo: planoAcao.tipo,
      descricao: planoAcao.descricao,
      classificacao: planoAcao.classificacao,
      mensagemAlterta: planoAcao.mensagemAlterta
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoAcao = this.createFromForm();
    if (planoAcao.id !== undefined) {
      this.subscribeToSaveResponse(this.planoAcaoService.update(planoAcao));
    } else {
      this.subscribeToSaveResponse(this.planoAcaoService.create(planoAcao));
    }
  }

  private createFromForm(): IPlanoAcao {
    return {
      ...new PlanoAcao(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      classificacao: this.editForm.get(['classificacao'])!.value,
      mensagemAlterta: this.editForm.get(['mensagemAlterta'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoAcao>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
