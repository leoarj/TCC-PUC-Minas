package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.UUID;

/**
 * A Incidente.
 */
@Entity
@Table(name = "incidente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incidente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "identificador", length = 36, nullable = false)
    private UUID identificador;

    @NotNull
    @Column(name = "data", nullable = false)
    private Instant data;

    @NotNull
    @Column(name = "classificacao", nullable = false)
    private Integer classificacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("incidentes")
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

    public Incidente identificador(UUID identificador) {
        this.identificador = identificador;
        return this;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = identificador;
    }

    public Instant getData() {
        return data;
    }

    public Incidente data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public Incidente classificacao(Integer classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Barragem getBarragem() {
        return barragem;
    }

    public Incidente barragem(Barragem barragem) {
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
        if (!(o instanceof Incidente)) {
            return false;
        }
        return id != null && id.equals(((Incidente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Incidente{" +
            "id=" + getId() +
            ", identificador='" + getIdentificador() + "'" +
            ", data='" + getData() + "'" +
            ", classificacao=" + getClassificacao() +
            "}";
    }
}
