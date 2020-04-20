import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISensor } from 'app/shared/model/ApiMonitoramentoBarragens/sensor.model';

@Component({
  selector: 'jhi-sensor-detail',
  templateUrl: './sensor-detail.component.html'
})
export class SensorDetailComponent implements OnInit {
  sensor: ISensor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sensor }) => (this.sensor = sensor));
  }

  previousState(): void {
    window.history.back();
  }
}
