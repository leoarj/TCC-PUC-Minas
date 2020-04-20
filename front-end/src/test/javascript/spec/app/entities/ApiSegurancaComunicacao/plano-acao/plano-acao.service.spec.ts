import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PlanoAcaoService } from 'app/entities/ApiSegurancaComunicacao/plano-acao/plano-acao.service';
import { IPlanoAcao, PlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';
import { TipoPlanoAcao } from 'app/shared/model/enumerations/tipo-plano-acao.model';

describe('Service Tests', () => {
  describe('PlanoAcao Service', () => {
    let injector: TestBed;
    let service: PlanoAcaoService;
    let httpMock: HttpTestingController;
    let elemDefault: IPlanoAcao;
    let expectedResult: IPlanoAcao | IPlanoAcao[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PlanoAcaoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PlanoAcao(0, TipoPlanoAcao.SMS, 'AAAAAAA', 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PlanoAcao', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PlanoAcao()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PlanoAcao', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            descricao: 'BBBBBB',
            classificacao: 1,
            mensagemAlterta: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PlanoAcao', () => {
        const returnedFromService = Object.assign(
          {
            tipo: 'BBBBBB',
            descricao: 'BBBBBB',
            classificacao: 1,
            mensagemAlterta: 'BBBBBB'
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

      it('should delete a PlanoAcao', () => {
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
