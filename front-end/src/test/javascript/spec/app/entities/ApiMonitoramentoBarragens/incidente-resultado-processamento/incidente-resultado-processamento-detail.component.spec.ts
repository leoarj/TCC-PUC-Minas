import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { IncidenteResultadoProcessamentoDetailComponent } from 'app/entities/ApiMonitoramentoBarragens/incidente-resultado-processamento/incidente-resultado-processamento-detail.component';
import { IncidenteResultadoProcessamento } from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';

describe('Component Tests', () => {
  describe('IncidenteResultadoProcessamento Management Detail Component', () => {
    let comp: IncidenteResultadoProcessamentoDetailComponent;
    let fixture: ComponentFixture<IncidenteResultadoProcessamentoDetailComponent>;
    const route = ({ data: of({ incidenteResultadoProcessamento: new IncidenteResultadoProcessamento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [IncidenteResultadoProcessamentoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IncidenteResultadoProcessamentoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidenteResultadoProcessamentoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incidenteResultadoProcessamento on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incidenteResultadoProcessamento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
