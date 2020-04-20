import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAfetado, Afetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';
import { AfetadoService } from './afetado.service';
import { IBarragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';
import { BarragemService } from 'app/entities/ApiMonitoramentoBarragens/barragem/barragem.service';

@Component({
  selector: 'jhi-afetado-update',
  templateUrl: './afetado-update.component.html'
})
export class AfetadoUpdateComponent implements OnInit {
  isSaving = false;
  barragems: IBarragem[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
    email: [null, [Validators.required, Validators.maxLength(130)]],
    telefonePrincipal: [null, [Validators.required, Validators.maxLength(16)]],
    telefoneReserva: [null, [Validators.required, Validators.maxLength(16)]],
    barragemId: [null, Validators.required]
  });

  constructor(
    protected afetadoService: AfetadoService,
    protected barragemService: BarragemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afetado }) => {
      this.updateForm(afetado);

      this.barragemService.query().subscribe((res: HttpResponse<IBarragem[]>) => (this.barragems = res.body || []));
    });
  }

  updateForm(afetado: IAfetado): void {
    this.editForm.patchValue({
      id: afetado.id,
      nome: afetado.nome,
      email: afetado.email,
      telefonePrincipal: afetado.telefonePrincipal,
      telefoneReserva: afetado.telefoneReserva,
      barragemId: afetado.barragemId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const afetado = this.createFromForm();
    if (afetado.id !== undefined) {
      this.subscribeToSaveResponse(this.afetadoService.update(afetado));
    } else {
      this.subscribeToSaveResponse(this.afetadoService.create(afetado));
    }
  }

  private createFromForm(): IAfetado {
    return {
      ...new Afetado(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      email: this.editForm.get(['email'])!.value,
      telefonePrincipal: this.editForm.get(['telefonePrincipal'])!.value,
      telefoneReserva: this.editForm.get(['telefoneReserva'])!.value,
      barragemId: this.editForm.get(['barragemId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAfetado>>): void {
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

  trackById(index: number, item: IBarragem): any {
    return item.id;
  }
}
