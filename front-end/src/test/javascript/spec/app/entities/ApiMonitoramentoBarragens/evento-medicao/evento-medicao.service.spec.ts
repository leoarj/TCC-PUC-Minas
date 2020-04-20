import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EventoMedicaoService } from 'app/entities/ApiMonitoramentoBarragens/evento-medicao/evento-medicao.service';
import { IEventoMedicao, EventoMedicao } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao.model';
import { TipoMedicao } from 'app/shared/model/enumerations/tipo-medicao.model';

describe('Service Tests', () => {
  describe('EventoMedicao Service', () => {
    let injector: TestBed;
    let service: EventoMedicaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventoMedicao;
    let expectedResult: IEventoMedicao | IEventoMedicao[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventoMedicaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new EventoMedicao(0, 'AAAAAAA', 'AAAAAAA', TipoMedicao.NIVEL_ARMAZENAMENTO, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should return a list of EventoMedicao', () => {
        const returnedFromService = Object.assign(
          {
            identificador: 'BBBBBB',
            sensorIdentificador: 'BBBBBB',
            tipo: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            intensidade: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
