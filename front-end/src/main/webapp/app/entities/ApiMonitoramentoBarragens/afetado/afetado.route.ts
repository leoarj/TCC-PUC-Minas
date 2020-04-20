import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAfetado, Afetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';
import { AfetadoService } from './afetado.service';
import { AfetadoComponent } from './afetado.component';
import { AfetadoDetailComponent } from './afetado-detail.component';
import { AfetadoUpdateComponent } from './afetado-update.component';

@Injectable({ providedIn: 'root' })
export class AfetadoResolve implements Resolve<IAfetado> {
  constructor(private service: AfetadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAfetado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((afetado: HttpResponse<Afetado>) => {
          if (afetado.body) {
            return of(afetado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Afetado());
  }
}

export const afetadoRoute: Routes = [
  {
    path: '',
    component: AfetadoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiMonitoramentoBarragensAfetado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AfetadoDetailComponent,
    resolve: {
      afetado: AfetadoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensAfetado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AfetadoUpdateComponent,
    resolve: {
      afetado: AfetadoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensAfetado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AfetadoUpdateComponent,
    resolve: {
      afetado: AfetadoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensAfetado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
