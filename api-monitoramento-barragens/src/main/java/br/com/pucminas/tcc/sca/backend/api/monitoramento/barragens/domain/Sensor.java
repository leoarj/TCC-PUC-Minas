package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A Sensor.
 */
@Entity
@Table(name = "sensor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "identificador", length = 36, nullable = false, unique = true)
    private UUID identificador;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMedicao tipo;

    @Size(max = 255)
    @Column(name = "observacoes", length = 255)
    private String observacoes;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("sensors")
    private Barragem barragem;

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

    public Sensor identificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public Sensor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoMedicao getTipo() {
        return tipo;
    }

    public Sensor tipo(TipoMedicao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMedicao tipo) {
        this.tipo = tipo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Sensor observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Barragem getBarragem() {
        return barragem;
    }

    public Sensor barragem(Barragem barragem) {
        this.barragem = barragem;
        return this;
    }

    public void setBarragem(Barragem barragem) {
        this.barragem = barragem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sensor)) {
            return false;
        }
        return id != null && id.equals(((Sensor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", nome='" + getNome() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            "}";
    }
}
