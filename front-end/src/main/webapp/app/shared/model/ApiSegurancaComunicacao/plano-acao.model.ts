import { TipoPlanoAcao } from 'app/shared/model/enumerations/tipo-plano-acao.model';

export interface IPlanoAcao {
  id?: number;
  tipo?: TipoPlanoAcao;
  descricao?: string;
  classificacao?: number;
  mensagemAlterta?: string;
}

export class PlanoAcao implements IPlanoAcao {
  constructor(
    public id?: number,
    public tipo?: TipoPlanoAcao,
    public descricao?: string,
    public classificacao?: number,
    public mensagemAlterta?: string
  ) {}
}
