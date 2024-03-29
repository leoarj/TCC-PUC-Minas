import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IIncidenteResultadoProcessamento } from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IncidenteResultadoProcessamentoService } from './incidente-resultado-processamento.service';

@Component({
  selector: 'jhi-incidente-resultado-processamento',
  templateUrl: './incidente-resultado-processamento.component.html'
})
export class IncidenteResultadoProcessamentoComponent implements OnInit, OnDestroy {
  incidenteResultadoProcessamentos?: IIncidenteResultadoProcessamento[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected incidenteResultadoProcessamentoService: IncidenteResultadoProcessamentoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.incidenteResultadoProcessamentoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IIncidenteResultadoProcessamento[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInIncidenteResultadoProcessamentos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIncidenteResultadoProcessamento): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIncidenteResultadoProcessamentos(): void {
    this.eventSubscriber = this.eventManager.subscribe('incidenteResultadoProcessamentoListModification', () => this.loadPage());
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IIncidenteResultadoProcessamento[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/incidente-resultado-processamento'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.incidenteResultadoProcessamentos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
