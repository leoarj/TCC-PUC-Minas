import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import {
  IEventoMedicaoClassificacaoAlerta,
  EventoMedicaoClassificacaoAlerta
} from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';
import { EventoMedicaoClassificacaoAlertaService } from './evento-medicao-classificacao-alerta.service';

@Component({
  selector: 'jhi-evento-medicao-classificacao-alerta-update',
  templateUrl: './evento-medicao-classificacao-alerta-update.component.html'
})
export class EventoMedicaoClassificacaoAlertaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.required]],
    intensidade: [null, [Validators.required]],
    dispararAlertas: [null, [Validators.required]]
  });

  constructor(
    protected eventoMedicaoClassificacaoAlertaService: EventoMedicaoClassificacaoAlertaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventoMedicaoClassificacaoAlerta }) => {
      this.updateForm(eventoMedicaoClassificacaoAlerta);
    });
  }

  updateForm(eventoMedicaoClassificacaoAlerta: IEventoMedicaoClassificacaoAlerta): void {
    this.editForm.patchValue({
      id: eventoMedicaoClassificacaoAlerta.id,
      tipo: eventoMedicaoClassificacaoAlerta.tipo,
      intensidade: eventoMedicaoClassificacaoAlerta.intensidade,
      dispararAlertas: eventoMedicaoClassificacaoAlerta.dispararAlertas
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventoMedicaoClassificacaoAlerta = this.createFromForm();
    if (eventoMedicaoClassificacaoAlerta.id !== undefined) {
      this.subscribeToSaveResponse(this.eventoMedicaoClassificacaoAlertaService.update(eventoMedicaoClassificacaoAlerta));
    } else {
      this.subscribeToSaveResponse(this.eventoMedicaoClassificacaoAlertaService.create(eventoMedicaoClassificacaoAlerta));
    }
  }

  private createFromForm(): IEventoMedicaoClassificacaoAlerta {
    return {
      ...new EventoMedicaoClassificacaoAlerta(),
      id: this.editForm.get(['id'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      intensidade: this.editForm.get(['intensidade'])!.value,
      dispararAlertas: this.editForm.get(['dispararAlertas'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventoMedicaoClassificacaoAlerta>>): void {
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
