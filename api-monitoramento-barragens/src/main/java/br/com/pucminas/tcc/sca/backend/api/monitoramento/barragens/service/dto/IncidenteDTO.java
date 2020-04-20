package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Incidente} entity.
 */
public class IncidenteDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -47948272796660041L;

	private Long id;

    @NotNull
    private UUID identificador;

    @NotNull
    private Instant data;

    @NotNull
    private Integer classificacao;


    private Long barragemId;

    private String barragemNome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIdentificador() {
        return identificador;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Long getBarragemId() {
        return barragemId;
    }

    public void setBarragemId(Long barragemId) {
        this.barragemId = barragemId;
    }

    public String getBarragemNome() {
        return barragemNome;
    }

    public void setBarragemNome(String barragemNome) {
        this.barragemNome = barragemNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidenteDTO incidenteDTO = (IncidenteDTO) o;
        if (incidenteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidenteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidenteDTO{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", data='" + getData() + "'" +
            ", classificacao=" + getClassificacao() +
            ", barragemId=" + getBarragemId() +
            ", barragemNome='" + getBarragemNome() + "'" +
            "}";
    }
}
