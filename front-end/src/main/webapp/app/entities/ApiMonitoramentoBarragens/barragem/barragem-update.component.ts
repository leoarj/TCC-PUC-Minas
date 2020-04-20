import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBarragem, Barragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';
import { BarragemService } from './barragem.service';

@Component({
  selector: 'jhi-barragem-update',
  templateUrl: './barragem-update.component.html'
})
export class BarragemUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
    capacidadeMetrosCubicos: [null, [Validators.required, Validators.min(10)]],
    latitude: [],
    longitude: [],
    grauRisco: [null, [Validators.required]]
  });

  constructor(protected barragemService: BarragemService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ barragem }) => {
      this.updateForm(barragem);
    });
  }

  updateForm(barragem: IBarragem): void {
    this.editForm.patchValue({
      id: barragem.id,
      nome: barragem.nome,
      capacidadeMetrosCubicos: barragem.capacidadeMetrosCubicos,
      latitude: barragem.latitude,
      longitude: barragem.longitude,
      grauRisco: barragem.grauRisco
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const barragem = this.createFromForm();
    if (barragem.id !== undefined) {
      this.subscribeToSaveResponse(this.barragemService.update(barragem));
    } else {
      this.subscribeToSaveResponse(this.barragemService.create(barragem));
    }
  }

  private createFromForm(): IBarragem {
    return {
      ...new Barragem(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      capacidadeMetrosCubicos: this.editForm.get(['capacidadeMetrosCubicos'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      grauRisco: this.editForm.get(['grauRisco'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBarragem>>): void {
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
