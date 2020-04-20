import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PlanoAcaoService } from './plano-acao.service';
import { PlanoAcaoDeleteDialogComponent } from './plano-acao-delete-dialog.component';

@Component({
  selector: 'jhi-plano-acao',
  templateUrl: './plano-acao.component.html'
})
export class PlanoAcaoComponent implements OnInit, OnDestroy {
  planoAcaos?: IPlanoAcao[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected planoAcaoService: PlanoAcaoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.planoAcaoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPlanoAcao[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInPlanoAcaos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlanoAcao): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlanoAcaos(): void {
    this.eventSubscriber = this.eventManager.subscribe('planoAcaoListModification', () => this.loadPage());
  }

  delete(planoAcao: IPlanoAcao): void {
    const modalRef = this.modalService.open(PlanoAcaoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.planoAcao = planoAcao;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IPlanoAcao[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/plano-acao'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.planoAcaos = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
