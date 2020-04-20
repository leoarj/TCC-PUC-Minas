package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicao} entity.
 */
public class EventoMedicaoDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7825818650446044965L;

	private Long id;

    @NotNull
    private UUID identificador;

    @NotNull
    private UUID sensorIdentificador;

    @NotNull
    private TipoMedicao tipo;

    @NotNull
    private Instant data;

    @NotNull
    private Integer intensidade;

    
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

    public UUID getSensorIdentificador() {
        return sensorIdentificador;
    }

    public void setSensorIdentificador(UUID sensorIdentificador) {
        this.sensorIdentificador = sensorIdentificador;
    }

    public TipoMedicao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedicao tipo) {
        this.tipo = tipo;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Integer getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(Integer intensidade) {
        this.intensidade = intensidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventoMedicaoDTO eventoMedicaoDTO = (EventoMedicaoDTO) o;
        if (eventoMedicaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventoMedicaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventoMedicaoDTO{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", sensorIdentificador='" + getSensorIdentificador() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", intensidade=" + getIntensidade() +
            "}";
    }
}
