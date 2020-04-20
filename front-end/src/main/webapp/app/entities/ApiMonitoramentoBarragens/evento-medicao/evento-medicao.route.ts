import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventoMedicao, EventoMedicao } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao.model';
import { EventoMedicaoService } from './evento-medicao.service';
import { EventoMedicaoComponent } from './evento-medicao.component';
import { EventoMedicaoDetailComponent } from './evento-medicao-detail.component';

@Injectable({ providedIn: 'root' })
export class EventoMedicaoResolve implements Resolve<IEventoMedicao> {
  constructor(private service: EventoMedicaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventoMedicao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventoMedicao: HttpResponse<EventoMedicao>) => {
          if (eventoMedicao.body) {
            return of(eventoMedicao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventoMedicao());
  }
}

export const eventoMedicaoRoute: Routes = [
  {
    path: '',
    component: EventoMedicaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiMonitoramentoBarragensEventoMedicao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventoMedicaoDetailComponent,
    resolve: {
      eventoMedicao: EventoMedicaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensEventoMedicao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
