import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { FrontEndTestModule } from '../../../../test.module';
import { IncidenteResultadoProcessamentoComponent } from 'app/entities/ApiMonitoramentoBarragens/incidente-resultado-processamento/incidente-resultado-processamento.component';
import { IncidenteResultadoProcessamentoService } from 'app/entities/ApiMonitoramentoBarragens/incidente-resultado-processamento/incidente-resultado-processamento.service';
import { IncidenteResultadoProcessamento } from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';

describe('Component Tests', () => {
  describe('IncidenteResultadoProcessamento Management Component', () => {
    let comp: IncidenteResultadoProcessamentoComponent;
    let fixture: ComponentFixture<IncidenteResultadoProcessamentoComponent>;
    let service: IncidenteResultadoProcessamentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [IncidenteResultadoProcessamentoComponent],
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
        .overrideTemplate(IncidenteResultadoProcessamentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IncidenteResultadoProcessamentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IncidenteResultadoProcessamentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IncidenteResultadoProcessamento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.incidenteResultadoProcessamentos && comp.incidenteResultadoProcessamentos[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IncidenteResultadoProcessamento(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.incidenteResultadoProcessamentos && comp.incidenteResultadoProcessamentos[0]).toEqual(
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
