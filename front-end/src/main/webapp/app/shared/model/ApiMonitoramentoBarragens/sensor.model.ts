import { TipoMedicao } from 'app/shared/model/enumerations/tipo-medicao.model';

export interface ISensor {
  id?: number;
  identificador?: string;
  nome?: string;
  tipo?: TipoMedicao;
  observacoes?: string;
  barragemNome?: string;
  barragemId?: number;
}

export class Sensor implements ISensor {
  constructor(
    public id?: number,
    public identificador?: string,
    public nome?: string,
    public tipo?: TipoMedicao,
    public observacoes?: string,
    public barragemNome?: string,
    public barragemId?: number
  ) {}
}
