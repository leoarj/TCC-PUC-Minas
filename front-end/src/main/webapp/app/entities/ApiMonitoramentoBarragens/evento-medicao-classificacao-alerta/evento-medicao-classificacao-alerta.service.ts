import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventoMedicaoClassificacaoAlerta } from 'app/shared/model/ApiMonitoramentoBarragens/evento-medicao-classificacao-alerta.model';

type EntityResponseType = HttpResponse<IEventoMedicaoClassificacaoAlerta>;
type EntityArrayResponseType = HttpResponse<IEventoMedicaoClassificacaoAlerta[]>;

@Injectable({ providedIn: 'root' })
export class EventoMedicaoClassificacaoAlertaService {
  public resourceUrl = SERVER_API_URL + 'services/api-monitoramento-barragens/api/evento-medicao-classificacoes-alerta';

  constructor(protected http: HttpClient) {}

  create(eventoMedicaoClassificacaoAlerta: IEventoMedicaoClassificacaoAlerta): Observable<EntityResponseType> {
    return this.http.post<IEventoMedicaoClassificacaoAlerta>(this.resourceUrl, eventoMedicaoClassificacaoAlerta, { observe: 'response' });
  }

  update(eventoMedicaoClassificacaoAlerta: IEventoMedicaoClassificacaoAlerta): Observable<EntityResponseType> {
    return this.http.put<IEventoMedicaoClassificacaoAlerta>(this.resourceUrl, eventoMedicaoClassificacaoAlerta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventoMedicaoClassificacaoAlerta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventoMedicaoClassificacaoAlerta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
