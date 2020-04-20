import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { AfetadoUpdateComponent } from 'app/entities/ApiMonitoramentoBarragens/afetado/afetado-update.component';
import { AfetadoService } from 'app/entities/ApiMonitoramentoBarragens/afetado/afetado.service';
import { Afetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';

describe('Component Tests', () => {
  describe('Afetado Management Update Component', () => {
    let comp: AfetadoUpdateComponent;
    let fixture: ComponentFixture<AfetadoUpdateComponent>;
    let service: AfetadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [AfetadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AfetadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AfetadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AfetadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Afetado(123);
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
        const entity = new Afetado();
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
