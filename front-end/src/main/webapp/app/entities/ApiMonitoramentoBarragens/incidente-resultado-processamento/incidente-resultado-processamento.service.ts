import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncidenteResultadoProcessamento } from 'app/shared/model/ApiMonitoramentoBarragens/incidente-resultado-processamento.model';

type EntityResponseType = HttpResponse<IIncidenteResultadoProcessamento>;
type EntityArrayResponseType = HttpResponse<IIncidenteResultadoProcessamento[]>;

@Injectable({ providedIn: 'root' })
export class IncidenteResultadoProcessamentoService {
  public resourceUrl = SERVER_API_URL + 'services/api-monitoramento-barragens/api/incidente-resultados-processamento';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncidenteResultadoProcessamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncidenteResultadoProcessamento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(incidenteResultadoProcessamento: IIncidenteResultadoProcessamento): IIncidenteResultadoProcessamento {
    const copy: IIncidenteResultadoProcessamento = Object.assign({}, incidenteResultadoProcessamento, {
      data:
        incidenteResultadoProcessamento.data && incidenteResultadoProcessamento.data.isValid()
          ? incidenteResultadoProcessamento.data.toJSON()
          : undefined
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
      res.body.forEach((incidenteResultadoProcessamento: IIncidenteResultadoProcessamento) => {
        incidenteResultadoProcessamento.data = incidenteResultadoProcessamento.data
          ? moment(incidenteResultadoProcessamento.data)
          : undefined;
      });
    }
    return res;
  }
}
