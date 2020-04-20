import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBarragem, Barragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';
import { BarragemService } from './barragem.service';
import { BarragemComponent } from './barragem.component';
import { BarragemDetailComponent } from './barragem-detail.component';
import { BarragemUpdateComponent } from './barragem-update.component';

@Injectable({ providedIn: 'root' })
export class BarragemResolve implements Resolve<IBarragem> {
  constructor(private service: BarragemService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBarragem> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((barragem: HttpResponse<Barragem>) => {
          if (barragem.body) {
            return of(barragem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Barragem());
  }
}

export const barragemRoute: Routes = [
  {
    path: '',
    component: BarragemComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiMonitoramentoBarragensBarragem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BarragemDetailComponent,
    resolve: {
      barragem: BarragemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensBarragem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BarragemUpdateComponent,
    resolve: {
      barragem: BarragemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensBarragem.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BarragemUpdateComponent,
    resolve: {
      barragem: BarragemResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiMonitoramentoBarragensBarragem.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
