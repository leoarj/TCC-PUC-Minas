import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventoMedicao } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao.model';

type EntityResponseType = HttpResponse<IEventoMedicao>;
type EntityArrayResponseType = HttpResponse<IEventoMedicao[]>;

@Injectable({ providedIn: 'root' })
export class EventoMedicaoService {
  public resourceUrl = SERVER_API_URL + 'services/api-monitoramento-barragens/api/eventos-medicao';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEventoMedicao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEventoMedicao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(eventoMedicao: IEventoMedicao): IEventoMedicao {
    const copy: IEventoMedicao = Object.assign({}, eventoMedicao, {
      data: eventoMedicao.data && eventoMedicao.data.isValid() ? eventoMedicao.data.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data ? moment(res.body.data) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((eventoMedicao: IEventoMedicao) => {
        eventoMedicao.data = eventoMedicao.data ? moment(eventoMedicao.data) : undefined;
      });
    }
    return res;
  }
}
