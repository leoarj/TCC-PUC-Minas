import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { FrontEndTestModule } from '../../../../test.module';
import { EventoMedicaoComponent } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao/evento-medicao.component';
import { EventoMedicaoService } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao/evento-medicao.service';
import { EventoMedicao } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao.model';

describe('Component Tests', () => {
  describe('EventoMedicao Management Component', () => {
    let comp: EventoMedicaoComponent;
    let fixture: ComponentFixture<EventoMedicaoComponent>;
    let service: EventoMedicaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [EventoMedicaoComponent],
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
        .overrideTemplate(EventoMedicaoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventoMedicaoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventoMedicaoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventoMedicao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventoMedicaos && comp.eventoMedicaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EventoMedicao(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.eventoMedicaos && comp.eventoMedicaos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
