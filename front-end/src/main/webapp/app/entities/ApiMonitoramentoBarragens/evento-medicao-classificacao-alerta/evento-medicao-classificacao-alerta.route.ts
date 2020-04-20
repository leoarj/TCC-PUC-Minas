import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import {
  IEventoMedicaoClassificacaoAlerta,
  EventoMedicaoClassificacaoAlerta
} from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';
import { EventoMedicaoClassificacaoAlertaService } from './evento-medicao-classificacao-alerta.service';
import { EventoMedicaoClassificacaoAlertaComponent } from './evento-medicao-classificacao-alerta.component';
import { EventoMedicaoClassificacaoAlertaDetailComponent } from './evento-medicao-classificacao-alerta-detail.component';
import { EventoMedicaoClassificacaoAlertaUpdateComponent } from './evento-medicao-classificacao-alerta-update.component';

@Injectable({ providedIn: 'root' })
export class EventoMedicaoClassificacaoAlertaResolve implements Resolve<IEventoMedicaoClassificacaoAlerta> {
  constructor(private service: EventoMedicaoClassificacaoAlertaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventoMedicaoClassificacaoAlerta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventoMedicaoClassificacaoAlerta: HttpResponse<EventoMedicaoClassificacaoAlerta>) => {
          if (eventoMedicaoClassificacaoAlerta.body) {
            return of(eventoMedicaoClassificacaoAlerta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventoMedicaoClassificacaoAlerta());
  }
}

export const eventoMedicaoClassificacaoAlertaRoute: Routes = [
  {
    path: '',
    component: EventoMedicaoClassificacaoAlertaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiMonitoramentoBarragensEventoMedicaoClassificacaoAlerta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventoMedicaoClassificacaoAlertaDetailComponent,
    resolve: {
      eventoMedicaoClassificacaoAlerta: EventoMedicaoClassificacaoAlertaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensEventoMedicaoClassificacaoAlerta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventoMedicaoClassificacaoAlertaUpdateComponent,
    resolve: {
      eventoMedicaoClassificacaoAlerta: EventoMedicaoClassificacaoAlertaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensEventoMedicaoClassificacaoAlerta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventoMedicaoClassificacaoAlertaUpdateComponent,
    resolve: {
      eventoMedicaoClassificacaoAlerta: EventoMedicaoClassificacaoAlertaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensEventoMedicaoClassificacaoAlerta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
