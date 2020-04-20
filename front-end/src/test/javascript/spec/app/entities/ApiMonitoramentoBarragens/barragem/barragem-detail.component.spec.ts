import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { BarragemDetailComponent } from 'app/entities/ApiMonitoramentoBarragens/barragem/barragem-detail.component';
import { Barragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';

describe('Component Tests', () => {
  describe('Barragem Management Detail Component', () => {
    let comp: BarragemDetailComponent;
    let fixture: ComponentFixture<BarragemDetailComponent>;
    const route = ({ data: of({ barragem: new Barragem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [BarragemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BarragemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BarragemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load barragem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.barragem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
