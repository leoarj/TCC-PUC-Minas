import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FrontEndTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { EventoMedicaoClassificacaoAlertaDeleteDialogComponent } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta-delete-dialog.component';
import { EventoMedicaoClassificacaoAlertaService } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta.service';

describe('Component Tests', () => {
  describe('EventoMedicaoClassificacaoAlerta Management Delete Component', () => {
    let comp: EventoMedicaoClassificacaoAlertaDeleteDialogComponent;
    let fixture: ComponentFixture<EventoMedicaoClassificacaoAlertaDeleteDialogComponent>;
    let service: EventoMedicaoClassificacaoAlertaService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FrontEndTestModule],
        declarations: [EventoMedicaoClassificacaoAlertaDeleteDialogComponent]
      })
        .overrideTemplate(EventoMedicaoClassificacaoAlertaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EventoMedicaoClassificacaoAlertaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EventoMedicaoClassificacaoAlertaService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
