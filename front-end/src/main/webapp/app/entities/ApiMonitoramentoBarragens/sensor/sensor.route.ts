import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISensor, Sensor } from 'app/shared/model/ApiMonitoramentoBarragens/sensor.model';
import { SensorService } from './sensor.service';
import { SensorComponent } from './sensor.component';
import { SensorDetailComponent } from './sensor-detail.component';
import { SensorUpdateComponent } from './sensor-update.component';

@Injectable({ providedIn: 'root' })
export class SensorResolve implements Resolve<ISensor> {
  constructor(private service: SensorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISensor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sensor: HttpResponse<Sensor>) => {
          if (sensor.body) {
            return of(sensor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sensor());
  }
}

export const sensorRoute: Routes = [
  {
    path: '',
    component: SensorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiMonitoramentoBarragensSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SensorDetailComponent,
    resolve: {
      sensor: SensorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SensorUpdateComponent,
    resolve: {
      sensor: SensorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SensorUpdateComponent,
    resolve: {
      sensor: SensorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensSensor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
