package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto.external;

import java.time.Instant;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.UUID;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.EventoMedicao} entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoMedicaoResumoDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 919122189297228170L;

	@NotNull
    private UUID sensorIdentificador;

    @NotNull
    private TipoMedicao tipo;

    private Instant data;

    @NotNull
    private Integer intensidade;

    public EventoMedicaoResumoDTO() {
    	super();
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoMedicaoResumoDTO other = (EventoMedicaoResumoDTO) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (intensidade == null) {
			if (other.intensidade != null)
				return false;
		} else if (!intensidade.equals(other.intensidade))
			return false;
		if (sensorIdentificador == null) {
			if (other.sensorIdentificador != null)
				return false;
		} else if (!sensorIdentificador.equals(other.sensorIdentificador))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((intensidade == null) ? 0 : intensidade.hashCode());
		result = prime * result + ((sensorIdentificador == null) ? 0 : sensorIdentificador.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
    public String toString() {
        return "EventoMedicaoDTO{" +
            " sensorIdentificador='" + getSensorIdentificador() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", intensidade=" + getIntensidade() +
            "}";
    }
}
