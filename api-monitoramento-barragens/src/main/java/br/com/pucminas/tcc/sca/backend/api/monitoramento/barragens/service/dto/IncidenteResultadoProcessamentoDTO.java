package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.IncidenteResultadoProcessamento} entity.
 */
public class IncidenteResultadoProcessamentoDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6815478541618457887L;

	private Long id;

    @NotNull
    private UUID incidenteIdentificador;

    @NotNull
    private Integer incidenteClassificacao;

    @NotNull
    private Boolean sucesso;

    @NotNull
    private Instant data;

    @NotNull
    @Size(min = 3, max = 10000)
    private String mensagem;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIncidenteIdentificador() {
        return incidenteIdentificador;
    }

    public void setIncidenteIdentificador(UUID incidenteIdentificador) {
        this.incidenteIdentificador = incidenteIdentificador;
    }

    public Integer getIncidenteClassificacao() {
        return incidenteClassificacao;
    }

    public void setIncidenteClassificacao(Integer incidenteClassificacao) {
        this.incidenteClassificacao = incidenteClassificacao;
    }

    public Boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidenteResultadoProcessamentoDTO incidenteResultadoProcessamentoDTO = (IncidenteResultadoProcessamentoDTO) o;
        if (incidenteResultadoProcessamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidenteResultadoProcessamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidenteResultadoProcessamentoDTO{" +
            "id=" + getId() +
            ", incidenteIdentificador='" + getIncidenteIdentificador() + "'" +
            ", incidenteClassificacao=" + getIncidenteClassificacao() +
            ", sucesso='" + isSucesso() + "'" +
            ", data='" + getData() + "'" +
            ", mensagem='" + getMensagem() + "'" +
            "}";
    }
}
