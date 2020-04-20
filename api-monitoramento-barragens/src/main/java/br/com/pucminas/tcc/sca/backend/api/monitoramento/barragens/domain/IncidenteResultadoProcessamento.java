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

/**
 * A IncidenteResultadoProcessamento.
 */
@Entity
@Table(name = "incidente_resultado_processamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IncidenteResultadoProcessamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "incidente_identificador", length = 36, nullable = false)
    private UUID incidenteIdentificador;

    @NotNull
    @Column(name = "incidente_classificacao", nullable = false)
    private Integer incidenteClassificacao;

    @NotNull
    @Column(name = "sucesso", nullable = false)
    private Boolean sucesso;

    @NotNull
    @Column(name = "data", nullable = false)
    private Instant data;

    @NotNull
    @Size(min = 3, max = 10000)
    @Column(name = "mensagem", length = 10000, nullable = false)
    private String mensagem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getIncidenteIdentificador() {
        return incidenteIdentificador;
    }

    public IncidenteResultadoProcessamento incidenteIdentificador(UUID incidenteIdentificador) {
        this.incidenteIdentificador = incidenteIdentificador;
        return this;
    }

    public void setIncidenteIdentificador(UUID incidenteIdentificador) {
        this.incidenteIdentificador = incidenteIdentificador;
    }

    public Integer getIncidenteClassificacao() {
        return incidenteClassificacao;
    }

    public IncidenteResultadoProcessamento incidenteClassificacao(Integer incidenteClassificacao) {
        this.incidenteClassificacao = incidenteClassificacao;
        return this;
    }

    public void setIncidenteClassificacao(Integer incidenteClassificacao) {
        this.incidenteClassificacao = incidenteClassificacao;
    }

    public Boolean isSucesso() {
        return sucesso;
    }

    public IncidenteResultadoProcessamento sucesso(Boolean sucesso) {
        this.sucesso = sucesso;
        return this;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public Instant getData() {
        return data;
    }

    public IncidenteResultadoProcessamento data(Instant data) {
        this.data = data;
        return this;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public IncidenteResultadoProcessamento mensagem(String mensagem) {
        this.mensagem = mensagem;
        return this;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncidenteResultadoProcessamento)) {
            return false;
        }
        return id != null && id.equals(((IncidenteResultadoProcessamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IncidenteResultadoProcessamento{" +
            "id=" + getId() +
            ", incidenteIdentificador='" + getIncidenteIdentificador() + "'" +
            ", incidenteClassificacao=" + getIncidenteClassificacao() +
            ", sucesso='" + isSucesso() + "'" +
            ", data='" + getData() + "'" +
            ", mensagem='" + getMensagem() + "'" +
            "}";
    }
}
