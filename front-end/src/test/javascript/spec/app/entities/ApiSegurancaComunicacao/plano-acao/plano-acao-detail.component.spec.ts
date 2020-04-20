import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { PlanoAcaoDetailComponent } from 'app/entities/ApiSegurancaComunicacao/plano-acao/plano-acao-detail.component';
import { PlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';

describe('Component Tests', () => {
  describe('PlanoAcao Management Detail Component', () => {
    let comp: PlanoAcaoDetailComponent;
    let fixture: ComponentFixture<PlanoAcaoDetailComponent>;
    const route = ({ data: of({ planoAcao: new PlanoAcao(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [PlanoAcaoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlanoAcaoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlanoAcaoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load planoAcao on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.planoAcao).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
