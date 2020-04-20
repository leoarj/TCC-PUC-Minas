package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external;

import javax.validation.constraints.*;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.BarragemDTO;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem} entity.
 */
public class DadosExecucaoPlanoAcaoDTO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5074124359474595803L;

	@NotNull
	private UUID identificador;
	
	@NotNull
    private Integer classificacao;

    @NotNull
    private BarragemDTO barragem;   	

    public UUID getIdentificador() {
		return identificador;
	}

	public void setIdentificador(UUID identificador) {
		this.identificador = identificador;
	}
    
    public Integer getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}

	public BarragemDTO getBarragem() {
		return barragem;
	}

	public void setBarragem(BarragemDTO barragem) {
		this.barragem = barragem;
	}	
	
	@Override
	public String toString() {
		return "DadosExecucaoPlanoAcaoDTO{" +	       
				"identificador=" + getIdentificador() +
	            ", classificacao=" + getClassificacao() +
	            ", barragem='" + getBarragem() + "'" +
	            	//"afetados: [" +
	            	//barragem.getAfetados() + "]'" +
	            "}";
	}
}
