import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IncidenteResultadoProcessamentoService } from 'app/entities/ApiMonitoramentoBarragens/incidente-resultado-processamento/incidente-resultado-processamento.service';
import {
  IIncidenteResultadoProcessamento,
  IncidenteResultadoProcessamento
} from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';

describe('Service Tests', () => {
  describe('IncidenteResultadoProcessamento Service', () => {
    let injector: TestBed;
    let service: IncidenteResultadoProcessamentoService;
    let httpMock: HttpTestingController;
    let elemDefault: IIncidenteResultadoProcessamento;
    let expectedResult: IIncidenteResultadoProcessamento | IIncidenteResultadoProcessamento[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IncidenteResultadoProcessamentoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IncidenteResultadoProcessamento(0, 'AAAAAAA', 0, false, currentDate, 'AAAAAAA');
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

      it('should return a list of IncidenteResultadoProcessamento', () => {
        const returnedFromService = Object.assign(
          {
            incidenteIdentificador: 'BBBBBB',
            incidenteClassificacao: 1,
            sucesso: true,
            data: currentDate.format(DATE_TIME_FORMAT),
            mensagem: 'BBBBBB'
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
