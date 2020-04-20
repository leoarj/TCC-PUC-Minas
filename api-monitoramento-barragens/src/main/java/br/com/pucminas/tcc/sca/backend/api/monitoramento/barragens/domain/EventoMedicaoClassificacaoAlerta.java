package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.TipoMedicao;

/**
 * A EventoMedicaoClassificacaoAlerta.
 */
@Entity
@Table(name = "evento_medicao_classificacao_alerta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventoMedicaoClassificacaoAlerta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMedicao tipo;

    @NotNull
    @Column(name = "intensidade", nullable = false)
    private Integer intensidade;

    @NotNull
    @Column(name = "disparar_alertas", nullable = false)
    private Boolean dispararAlertas;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMedicao getTipo() {
        return tipo;
    }

    public EventoMedicaoClassificacaoAlerta tipo(TipoMedicao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoMedicao tipo) {
        this.tipo = tipo;
    }

    public Integer getIntensidade() {
        return intensidade;
    }

    public EventoMedicaoClassificacaoAlerta intensidade(Integer intensidade) {
        this.intensidade = intensidade;
        return this;
    }

    public void setIntensidade(Integer intensidade) {
        this.intensidade = intensidade;
    }

    public Boolean isDispararAlertas() {
        return dispararAlertas;
    }

    public EventoMedicaoClassificacaoAlerta dispararAlertas(Boolean dispararAlertas) {
        this.dispararAlertas = dispararAlertas;
        return this;
    }

    public void setDispararAlertas(Boolean dispararAlertas) {
        this.dispararAlertas = dispararAlertas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventoMedicaoClassificacaoAlerta)) {
            return false;
        }
        return id != null && id.equals(((EventoMedicaoClassificacaoAlerta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventoMedicaoClassificacaoAlerta{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", intensidade=" + getIntensidade() +
            ", dispararAlertas='" + isDispararAlertas() + "'" +
            "}";
    }
}
