package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.UUID;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A EventoMedicao.
 */
@Entity
@Table(name = "evento_medicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventoMedicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "identificador", length = 36, nullable = false, unique = true)
    private UUID identificador;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "sensor_identificador", length = 36, nullable = false)
    private UUID sensorIdentificador;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMedicao tipo;

    @NotNull
    @Column(name = "data", nullable = false)
    private Instant data;

    @NotNull
    @Column(name = "intensidade", nullable = false)
    private Integer intensidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIdentificador() {
        return identificador;
    }

    public EventoMedicao identificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public UUID getSensorIdentificador() {
        return sensorIdentificador;
    }

    public EventoMedicao sensorIdentificador(UUID sensorIdentificador) {
        this.sensorIdentificador = sensorIdentificador;
        return this;
    }

    public void setSensorIdentificador(UUID sensorIdentificador) {
        this.sensorIdentificador = sensorIdentificador;
    }

    public TipoMedicao getTipo() {
        return tipo;
    }

    public EventoMedicao tipo(TipoMedicao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMedicao tipo) {
        this.tipo = tipo;
    }

    public Instant getData() {
        return data;
    }

    public EventoMedicao data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Integer getIntensidade() {
        return intensidade;
    }

    public EventoMedicao intensidade(Integer intensidade) {
        this.intensidade = intensidade;
        return this;
    }

    public void setIntensidade(Integer intensidade) {
        this.intensidade = intensidade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventoMedicao)) {
            return false;
        }
        return id != null && id.equals(((EventoMedicao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventoMedicao{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", sensorIdentificador='" + getSensorIdentificador() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", data='" + getData() + "'" +
            ", intensidade=" + getIntensidade() +
            "}";
    }
}
