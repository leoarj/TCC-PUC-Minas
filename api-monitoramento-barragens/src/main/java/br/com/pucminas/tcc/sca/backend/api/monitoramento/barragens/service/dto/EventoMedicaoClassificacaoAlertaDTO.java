package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicaoClassificacaoAlerta} entity.
 */
public class EventoMedicaoClassificacaoAlertaDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1292502494145724416L;

	private Long id;

    @NotNull
    private TipoMedicao tipo;

    @NotNull
    private Integer intensidade;

    @NotNull
    private Boolean dispararAlertas;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMedicao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedicao tipo) {
        this.tipo = tipo;
    }

    public Integer getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(Integer intensidade) {
        this.intensidade = intensidade;
    }

    public Boolean isDispararAlertas() {
        return dispararAlertas;
    }

    public void setDispararAlertas(Boolean dispararAlertas) {
        this.dispararAlertas = dispararAlertas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventoMedicaoClassificacaoAlertaDTO eventoMedicaoClassificacaoAlertaDTO = (EventoMedicaoClassificacaoAlertaDTO) o;
        if (eventoMedicaoClassificacaoAlertaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventoMedicaoClassificacaoAlertaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventoMedicaoClassificacaoAlertaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", intensidade=" + getIntensidade() +
            ", dispararAlertas='" + isDispararAlertas() + "'" +
            "}";
    }
}
