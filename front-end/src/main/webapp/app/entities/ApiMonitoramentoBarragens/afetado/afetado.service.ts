import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAfetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';

type EntityResponseType = HttpResponse<IAfetado>;
type EntityArrayResponseType = HttpResponse<IAfetado[]>;

@Injectable({ providedIn: 'root' })
export class AfetadoService {
  public resourceUrl = SERVER_API_URL + 'services/api-monitoramento-barragens/api/afetados';

  constructor(protected http: HttpClient) {}

  create(afetado: IAfetado): Observable<EntityResponseType> {
    return this.http.post<IAfetado>(this.resourceUrl, afetado, { observe: 'response' });
  }

  update(afetado: IAfetado): Observable<EntityResponseType> {
    return this.http.put<IAfetado>(this.resourceUrl, afetado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAfetado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAfetado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
