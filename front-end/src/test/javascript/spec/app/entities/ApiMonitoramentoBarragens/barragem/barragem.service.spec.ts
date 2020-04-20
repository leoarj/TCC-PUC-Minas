import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BarragemService } from 'app/entities/ApiMonitoramentoBarragens/barragem/barragem.service';
import { IBarragem, Barragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';
import { GrauRisco } from 'app/shared/model/enumerations/grau-risco.model';

describe('Service Tests', () => {
  describe('Barragem Service', () => {
    let injector: TestBed;
    let service: BarragemService;
    let httpMock: HttpTestingController;
    let elemDefault: IBarragem;
    let expectedResult: IBarragem | IBarragem[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BarragemService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Barragem(0, 'AAAAAAA', 0, 0, 0, GrauRisco.NENHUM);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Barragem', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Barragem()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Barragem', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            capacidadeMetrosCubicos: 1,
            latitude: 1,
            longitude: 1,
            grauRisco: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Barragem', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            capacidadeMetrosCubicos: 1,
            latitude: 1,
            longitude: 1,
            grauRisco: 'BBBBBB'
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

      it('should delete a Barragem', () => {
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
