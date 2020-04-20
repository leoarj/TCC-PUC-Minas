import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlanoAcao } from 'app/shared/model/ApiSegurancaComunicacao/plano-acao.model';

type EntityResponseType = HttpResponse<IPlanoAcao>;
type EntityArrayResponseType = HttpResponse<IPlanoAcao[]>;

@Injectable({ providedIn: 'root' })
export class PlanoAcaoService {
  public resourceUrl = SERVER_API_URL + 'services/api-seguranca-comunicacao/api/planos-acao';

  constructor(protected http: HttpClient) {}

  create(planoAcao: IPlanoAcao): Observable<EntityResponseType> {
    return this.http.post<IPlanoAcao>(this.resourceUrl, planoAcao, { observe: 'response' });
  }

  update(planoAcao: IPlanoAcao): Observable<EntityResponseType> {
    return this.http.put<IPlanoAcao>(this.resourceUrl, planoAcao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoAcao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoAcao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
