import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBarragem } from 'app/shared/model/ApiMonitoramentoBarragens/barragem.model';

type EntityResponseType = HttpResponse<IBarragem>;
type EntityArrayResponseType = HttpResponse<IBarragem[]>;

@Injectable({ providedIn: 'root' })
export class BarragemService {
  public resourceUrl = SERVER_API_URL + 'services/api-monitoramento-barragens/api/barragens';

  constructor(protected http: HttpClient) {}

  create(barragem: IBarragem): Observable<EntityResponseType> {
    return this.http.post<IBarragem>(this.resourceUrl, barragem, { observe: 'response' });
  }

  update(barragem: IBarragem): Observable<EntityResponseType> {
    return this.http.put<IBarragem>(this.resourceUrl, barragem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBarragem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarragem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
