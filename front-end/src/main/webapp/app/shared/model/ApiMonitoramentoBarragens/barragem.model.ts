import { ISensor } from 'app/shared/model/ApiMonitoramentoBarragens/sensor.model';
import { IIncidente } from 'app/shared/model/ApiMonitoramentoBarragens/incidente.model';
import { IAfetado } from 'app/shared/model/ApiMonitoramentoBarragens/afetado.model';
import { GrauRisco } from 'app/shared/model/enumerations/grau-risco.model';

export interface IBarragem {
  id?: number;
  nome?: string;
  capacidadeMetrosCubicos?: number;
  latitude?: number;
  longitude?: number;
  grauRisco?: GrauRisco;
  sensors?: ISensor[];
  incidentes?: IIncidente[];
  afetados?: IAfetado[];
}

export class Barragem implements IBarragem {
  constructor(
    public id?: number,
    public nome?: string,
    public capacidadeMetrosCubicos?: number,
    public latitude?: number,
    public longitude?: number,
    public grauRisco?: GrauRisco,
    public sensors?: ISensor[],
    public incidentes?: IIncidente[],
    public afetados?: IAfetado[]
  ) {}
}
