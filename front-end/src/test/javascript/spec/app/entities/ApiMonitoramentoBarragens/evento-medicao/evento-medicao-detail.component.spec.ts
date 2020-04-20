import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { EventoMedicaoDetailComponent } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao/evento-medicao-detail.component';
import { EventoMedicao } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao.model';

describe('Component Tests', () => {
  describe('EventoMedicao Management Detail Component', () => {
    let comp: EventoMedicaoDetailComponent;
    let fixture: ComponentFixture<EventoMedicaoDetailComponent>;
    const route = ({ data: of({ eventoMedicao: new EventoMedicao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [EventoMedicaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EventoMedicaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventoMedicaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load eventoMedicao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.eventoMedicao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
