import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IncidenteService } from 'app/entities/ApiMonitoramentoBarragens/incidente/incidente.service';
import { IIncidente, Incidente } from 'app/shared/model/ApiMonitoramentoBarragens/incidente.model';

describe('Service Tests', () => {
  describe('Incidente Service', () => {
    let injector: TestBed;
    let service: IncidenteService;
    let httpMock: HttpTestingController;
    let elemDefault: IIncidente;
    let expectedResult: IIncidente | IIncidente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IncidenteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Incidente(0, 'AAAAAAA', currentDate, 0);
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

      it('should return a list of Incidente', () => {
        const returnedFromService = Object.assign(
          {
            identificador: 'BBBBBB',
            data: currentDate.format(DATE_TIME_FORMAT),
            classificacao: 1
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
