import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { EventoMedicaoClassificacaoAlertaDetailComponent } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta-detail.component';
import { EventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';

describe('Component Tests', () => {
  describe('EventoMedicaoClassificacaoAlerta Management Detail Component', () => {
    let comp: EventoMedicaoClassificacaoAlertaDetailComponent;
    let fixture: ComponentFixture<EventoMedicaoClassificacaoAlertaDetailComponent>;
    const route = ({ data: of({ eventoMedicaoClassificacaoAlerta: new EventoMedicaoClassificacaoAlerta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [EventoMedicaoClassificacaoAlertaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventoMedicaoClassificacaoAlertaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventoMedicaoClassificacaoAlertaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eventoMedicaoClassificacaoAlerta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventoMedicaoClassificacaoAlerta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
