import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { FrontEndTestModule } from '../../../../test.module';
import { EventoMedicaoClassificacaoAlertaComponent } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta.component';
import { EventoMedicaoClassificacaoAlertaService } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta.service';
import { EventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';

describe('Component Tests', () => {
  describe('EventoMedicaoClassificacaoAlerta Management Component', () => {
    let comp: EventoMedicaoClassificacaoAlertaComponent;
    let fixture: ComponentFixture<EventoMedicaoClassificacaoAlertaComponent>;
    let service: EventoMedicaoClassificacaoAlertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [EventoMedicaoClassificacaoAlertaComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(EventoMedicaoClassificacaoAlertaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventoMedicaoClassificacaoAlertaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventoMedicaoClassificacaoAlertaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventoMedicaoClassificacaoAlerta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventoMedicaoClassificacaoAlertas && comp.eventoMedicaoClassificacaoAlertas[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventoMedicaoClassificacaoAlerta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventoMedicaoClassificacaoAlertas && comp.eventoMedicaoClassificacaoAlertas[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
