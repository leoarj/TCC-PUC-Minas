import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { IncidenteDetailComponent } from 'app/entities/ApiMonitoramentoBarragens/incidente/incidente-detail.component';
import { Incidente } from 'app/shared/model/ApiMonitoramentoBarragens/incidente.model';

describe('Component Tests', () => {
  describe('Incidente Management Detail Component', () => {
    let comp: IncidenteDetailComponent;
    let fixture: ComponentFixture<IncidenteDetailComponent>;
    const route = ({ data: of({ incidente: new Incidente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [IncidenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IncidenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incidente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incidente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
