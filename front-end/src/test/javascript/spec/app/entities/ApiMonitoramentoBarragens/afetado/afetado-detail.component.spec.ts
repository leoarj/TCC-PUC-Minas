import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { AfetadoDetailComponent } from 'app/entities/ApiMonitoramentoBarragens/afetado/afetado-detail.component';
import { Afetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';

describe('Component Tests', () => {
  describe('Afetado Management Detail Component', () => {
    let comp: AfetadoDetailComponent;
    let fixture: ComponentFixture<AfetadoDetailComponent>;
    const route = ({ data: of({ afetado: new Afetado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [AfetadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AfetadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AfetadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load afetado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.afetado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
