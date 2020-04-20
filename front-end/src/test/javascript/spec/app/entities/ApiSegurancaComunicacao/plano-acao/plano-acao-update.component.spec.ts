import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { PlanoAcaoUpdateComponent } from 'app/entities/ApiSegurancaComunicacao/plano-acao/plano-acao-update.component';
import { PlanoAcaoService } from 'app/entities/ApiSegurancaComunicacao/plano-acao/plano-acao.service';
import { PlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';

describe('Component Tests', () => {
  describe('PlanoAcao Management Update Component', () => {
    let comp: PlanoAcaoUpdateComponent;
    let fixture: ComponentFixture<PlanoAcaoUpdateComponent>;
    let service: PlanoAcaoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [PlanoAcaoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlanoAcaoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlanoAcaoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlanoAcaoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoAcao(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlanoAcao();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
