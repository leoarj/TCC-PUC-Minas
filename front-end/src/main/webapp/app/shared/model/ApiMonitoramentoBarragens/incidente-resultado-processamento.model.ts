import { Moment } from 'moment';

export interface IIncidenteResultadoProcessamento {
  id?: number;
  incidenteIdentificador?: string;
  incidenteClassificacao?: number;
  sucesso?: boolean;
  data?: Moment;
  mensagem?: string;
}

export class IncidenteResultadoProcessamento implements IIncidenteResultadoProcessamento {
  constructor(
    public id?: number,
    public incidenteIdentificador?: string,
    public incidenteClassificacao?: number,
    public sucesso?: boolean,
    public data?: Moment,
    public mensagem?: string
  ) {
    this.sucesso = this.sucesso || false;
  }
}
