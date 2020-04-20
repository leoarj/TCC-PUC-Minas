export interface IAfetado {
  id?: number;
  nome?: string;
  email?: string;
  telefonePrincipal?: string;
  telefoneReserva?: string;
  barragemNome?: string;
  barragemId?: number;
}

export class Afetado implements IAfetado {
  constructor(
    public id?: number,
    public nome?: string,
    public email?: string,
    public telefonePrincipal?: string,
    public telefoneReserva?: string,
    public barragemNome?: string,
    public barragemId?: number
  ) {}
}
