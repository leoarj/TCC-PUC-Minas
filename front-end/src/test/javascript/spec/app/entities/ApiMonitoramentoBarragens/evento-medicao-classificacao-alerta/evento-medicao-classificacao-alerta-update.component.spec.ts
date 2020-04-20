import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { FrontEndTestModule } from '../../../../test.module';
import { EventoMedicaoClassificacaoAlertaUpdateComponent } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta-update.component';
import { EventoMedicaoClassificacaoAlertaService } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta.service';
import { EventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';

describe('Component Tests', () => {
  describe('EventoMedicaoClassificacaoAlerta Management Update Component', () => {
    let comp: EventoMedicaoClassificacaoAlertaUpdateComponent;
    let fixture: ComponentFixture<EventoMedicaoClassificacaoAlertaUpdateComponent>;
    let service: EventoMedicaoClassificacaoAlertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [EventoMedicaoClassificacaoAlertaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EventoMedicaoClassificacaoAlertaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventoMedicaoClassificacaoAlertaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventoMedicaoClassificacaoAlertaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EventoMedicaoClassificacaoAlerta(123);
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
        const entity = new EventoMedicaoClassificacaoAlerta();
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
