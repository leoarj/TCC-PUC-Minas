package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.enumeration.TipoPlanoAcao;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.PlanoAcao} entity.
 */
public class PlanoAcaoDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 560337640634647832L;

	private Long id;

    private TipoPlanoAcao tipo;

    @NotNull
    @Size(min = 3, max = 60)
    private String descricao;

    @NotNull
    private Integer classificacao;

    @NotNull
    @Size(max = 130)
    private String mensagemAlterta;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPlanoAcao getTipo() {
        return tipo;
    }

    public void setTipo(TipoPlanoAcao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public String getMensagemAlterta() {
        return mensagemAlterta;
    }

    public void setMensagemAlterta(String mensagemAlterta) {
        this.mensagemAlterta = mensagemAlterta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanoAcaoDTO planoAcaoDTO = (PlanoAcaoDTO) o;
        if (planoAcaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planoAcaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanoAcaoDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", classificacao=" + getClassificacao() +
            ", mensagemAlterta='" + getMensagemAlterta() + "'" +
            "}";
    }
}
