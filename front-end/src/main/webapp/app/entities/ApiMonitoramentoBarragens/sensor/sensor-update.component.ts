import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISensor, Sensor } from 'app/shared/model/ApiMonitoramentoBarragens/sensor.model';
import { SensorService } from './sensor.service';
import { IBarragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';
import { BarragemService } from 'app/entities/ApiMonitoramentoBarragens/barragem/barragem.service';

@Component({
  selector: 'jhi-sensor-update',
  templateUrl: './sensor-update.component.html'
})
export class SensorUpdateComponent implements OnInit {
  isSaving = false;
  barragems: IBarragem[] = [];

  editForm = this.fb.group({
    id: [],
    identificador: [null, [Validators.required]],
    nome: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
    tipo: [null, [Validators.required]],
    observacoes: [null, [Validators.maxLength(255)]],
    barragemId: [null, Validators.required]
  });

  constructor(
    protected sensorService: SensorService,
    protected barragemService: BarragemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensor }) => {
      this.updateForm(sensor);

      this.barragemService.query().subscribe((res: HttpResponse<IBarragem[]>) => (this.barragems = res.body || []));
    });
  }

  updateForm(sensor: ISensor): void {
    this.editForm.patchValue({
      id: sensor.id,
      identificador: sensor.identificador,
      nome: sensor.nome,
      tipo: sensor.tipo,
      observacoes: sensor.observacoes,
      barragemId: sensor.barragemId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sensor = this.createFromForm();
    if (sensor.id !== undefined) {
      this.subscribeToSaveResponse(this.sensorService.update(sensor));
    } else {
      this.subscribeToSaveResponse(this.sensorService.create(sensor));
    }
  }

  private createFromForm(): ISensor {
    return {
      ...new Sensor(),
      id: this.editForm.get(['id'])!.value,
      identificador: this.editForm.get(['identificador'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      observacoes: this.editForm.get(['observacoes'])!.value,
      barragemId: this.editForm.get(['barragemId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISensor>>): void {
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
