import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncidente } from 'app/shared/model/ApiMonitoramentoBarragens/incidente.model';

type EntityResponseType = HttpResponse<IIncidente>;
type EntityArrayResponseType = HttpResponse<IIncidente[]>;

@Injectable({ providedIn: 'root' })
export class IncidenteService {
  public resourceUrl = SERVER_API_URL + 'services/api-monitoramento-barragens/api/incidentes';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncidente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncidente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(incidente: IIncidente): IIncidente {
    const copy: IIncidente = Object.assign({}, incidente, {
      data: incidente.data && incidente.data.isValid() ? incidente.data.toJSON() : undefined
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
      res.body.forEach((incidente: IIncidente) => {
        incidente.data = incidente.data ? moment(incidente.data) : undefined;
      });
    }
    return res;
  }
}
