import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  IIncidenteResultadoProcessamento,
  IncidenteResultadoProcessamento
} from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';
import { IncidenteResultadoProcessamentoService } from './incidente-resultado-processamento.service';
import { IncidenteResultadoProcessamentoComponent } from './incidente-resultado-processamento.component';
import { IncidenteResultadoProcessamentoDetailComponent } from './incidente-resultado-processamento-detail.component';

@Injectable({ providedIn: 'root' })
export class IncidenteResultadoProcessamentoResolve implements Resolve<IIncidenteResultadoProcessamento> {
  constructor(private service: IncidenteResultadoProcessamentoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncidenteResultadoProcessamento> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((incidenteResultadoProcessamento: HttpResponse<IncidenteResultadoProcessamento>) => {
          if (incidenteResultadoProcessamento.body) {
            return of(incidenteResultadoProcessamento.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IncidenteResultadoProcessamento());
  }
}

export const incidenteResultadoProcessamentoRoute: Routes = [
  {
    path: '',
    component: IncidenteResultadoProcessamentoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiMonitoramentoBarragensIncidenteResultadoProcessamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncidenteResultadoProcessamentoDetailComponent,
    resolve: {
      incidenteResultadoProcessamento: IncidenteResultadoProcessamentoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensIncidenteResultadoProcessamento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
