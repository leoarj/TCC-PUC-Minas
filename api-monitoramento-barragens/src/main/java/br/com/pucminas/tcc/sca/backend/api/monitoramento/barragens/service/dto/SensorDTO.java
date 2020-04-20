package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Sensor} entity.
 */
public class SensorDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2772993027883659643L;

	private Long id;

    @NotNull
    private UUID identificador;

    @NotNull
    @Size(min = 3, max = 60)
    private String nome;

    @NotNull
    private TipoMedicao tipo;

    @Size(max = 255)
    private String observacoes;


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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoMedicao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedicao tipo) {
        this.tipo = tipo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

        SensorDTO sensorDTO = (SensorDTO) o;
        if (sensorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sensorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SensorDTO{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", barragemId=" + getBarragemId() +
            ", barragemNome='" + getBarragemNome() + "'" +
            "}";
    }
}
