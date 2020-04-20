import { TipoMedicao } from 'app/shared/model/enumerations/tipo-medicao.model';

export interface IEventoMedicaoClassificacaoAlerta {
  id?: number;
  tipo?: TipoMedicao;
  intensidade?: number;
  dispararAlertas?: boolean;
}

export class EventoMedicaoClassificacaoAlerta implements IEventoMedicaoClassificacaoAlerta {
  constructor(public id?: number, public tipo?: TipoMedicao, public intensidade?: number, public dispararAlertas?: boolean) {
    this.dispararAlertas = this.dispararAlertas || false;
  }
}
