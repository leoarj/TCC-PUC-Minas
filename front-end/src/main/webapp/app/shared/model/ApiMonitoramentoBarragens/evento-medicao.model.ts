import { Moment } from 'moment';
import { TipoMedicao } from 'app/shared/model/enumerations/tipo-medicao.model';

export interface IEventoMedicao {
  id?: number;
  identificador?: string;
  sensorIdentificador?: string;
  tipo?: TipoMedicao;
  data?: Moment;
  intensidade?: number;
}

export class EventoMedicao implements IEventoMedicao {
  constructor(
    public id?: number,
    public identificador?: string,
    public sensorIdentificador?: string,
    public tipo?: TipoMedicao,
    public data?: Moment,
    public intensidade?: number
  ) {}
}
