import { Moment } from 'moment';

export interface IIncidente {
  id?: number;
  identificador?: string;
  data?: Moment;
  classificacao?: number;
  barragemNome?: string;
  barragemId?: number;
}

export class Incidente implements IIncidente {
  constructor(
    public id?: number,
    public identificador?: string,
    public data?: Moment,
    public classificacao?: number,
    public barragemNome?: string,
    public barragemId?: number
  ) {}
}
