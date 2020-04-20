import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlanoAcao, PlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';
import { PlanoAcaoService } from './plano-acao.service';
import { PlanoAcaoComponent } from './plano-acao.component';
import { PlanoAcaoDetailComponent } from './plano-acao-detail.component';
import { PlanoAcaoUpdateComponent } from './plano-acao-update.component';

@Injectable({ providedIn: 'root' })
export class PlanoAcaoResolve implements Resolve<IPlanoAcao> {
  constructor(private service: PlanoAcaoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlanoAcao> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((planoAcao: HttpResponse<PlanoAcao>) => {
          if (planoAcao.body) {
            return of(planoAcao.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlanoAcao());
  }
}

export const planoAcaoRoute: Routes = [
  {
    path: '',
    component: PlanoAcaoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'front-end-app.apiSegurancaComunicacaoPlanoAcao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanoAcaoDetailComponent,
    resolve: {
      planoAcao: PlanoAcaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiSegurancaComunicacaoPlanoAcao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanoAcaoUpdateComponent,
    resolve: {
      planoAcao: PlanoAcaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiSegurancaComunicacaoPlanoAcao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanoAcaoUpdateComponent,
    resolve: {
      planoAcao: PlanoAcaoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'front-end-app.apiSegurancaComunicacaoPlanoAcao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
