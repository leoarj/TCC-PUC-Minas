import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EventoMedicaoClassificacaoAlertaService } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta/evento-medicao-classificacao-alerta.service';
import {
  IEventoMedicaoClassificacaoAlerta,
  EventoMedicaoClassificacaoAlerta
} from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';
import { TipoMedicao } from 'app/shared/model/enumerations/tipo-medicao.model';

describe('Service Tests', () => {
  describe('EventoMedicaoClassificacaoAlerta Service', () => {
    let injector: TestBed;
    let service: EventoMedicaoClassificacaoAlertaService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventoMedicaoClassificacaoAlerta;
    let expectedResult: IEventoMedicaoClassificacaoAlerta | IEventoMedicaoClassificacaoAlerta[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventoMedicaoClassificacaoAlertaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EventoMedicaoClassificacaoAlerta(0, TipoMedicao.NIVEL_ARMAZENAMENTO, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EventoMedicaoClassificacaoAlerta', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EventoMedicaoClassificacaoAlerta()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EventoMedicaoClassificacaoAlerta', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            intensidade: 1,
            dispararAlertas: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EventoMedicaoClassificacaoAlerta', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            intensidade: 1,
            dispararAlertas: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EventoMedicaoClassificacaoAlerta', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
