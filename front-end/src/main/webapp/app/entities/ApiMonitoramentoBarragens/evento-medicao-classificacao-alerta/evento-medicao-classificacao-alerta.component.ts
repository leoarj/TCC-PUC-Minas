import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EventoMedicaoClassificacaoAlertaService } from './evento-medicao-classificacao-alerta.service';
import { EventoMedicaoClassificacaoAlertaDeleteDialogComponent } from './evento-medicao-classificacao-alerta-delete-dialog.component';

@Component({
  selector: 'jhi-evento-medicao-classificacao-alerta',
  templateUrl: './evento-medicao-classificacao-alerta.component.html'
})
export class EventoMedicaoClassificacaoAlertaComponent implements OnInit, OnDestroy {
  eventoMedicaoClassificacaoAlertas?: IEventoMedicaoClassificacaoAlerta[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected eventoMedicaoClassificacaoAlertaService: EventoMedicaoClassificacaoAlertaService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.eventoMedicaoClassificacaoAlertaService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEventoMedicaoClassificacaoAlerta[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEventoMedicaoClassificacaoAlertas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEventoMedicaoClassificacaoAlerta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEventoMedicaoClassificacaoAlertas(): void {
    this.eventSubscriber = this.eventManager.subscribe('eventoMedicaoClassificacaoAlertaListModification', () => this.loadPage());
  }

  delete(eventoMedicaoClassificacaoAlerta: IEventoMedicaoClassificacaoAlerta): void {
    const modalRef = this.modalService.open(EventoMedicaoClassificacaoAlertaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.eventoMedicaoClassificacaoAlerta = eventoMedicaoClassificacaoAlerta;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEventoMedicaoClassificacaoAlerta[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/evento-medicao-classificacao-alerta'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.eventoMedicaoClassificacaoAlertas = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
