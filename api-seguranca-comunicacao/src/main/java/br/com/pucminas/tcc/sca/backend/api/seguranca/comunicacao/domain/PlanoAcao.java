package br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import br.com.pucminas.tcc.sca.backend.api.seguranca.comunicacao.domain.enumeration.TipoPlanoAcao;

/**
 * A PlanoAcao.
 */
@Entity
@Table(name = "plano_acao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PlanoAcao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoPlanoAcao tipo;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "descricao", length = 60, nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "classificacao", nullable = false)
    private Integer classificacao;

    @NotNull
    @Size(max = 130)
    @Column(name = "mensagem_alterta", length = 130, nullable = false)
    private String mensagemAlterta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPlanoAcao getTipo() {
        return tipo;
    }

    public PlanoAcao tipo(TipoPlanoAcao tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoPlanoAcao tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public PlanoAcao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public PlanoAcao classificacao(Integer classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public String getMensagemAlterta() {
        return mensagemAlterta;
    }

    public PlanoAcao mensagemAlterta(String mensagemAlterta) {
        this.mensagemAlterta = mensagemAlterta;
        return this;
    }

    public void setMensagemAlterta(String mensagemAlterta) {
        this.mensagemAlterta = mensagemAlterta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoAcao)) {
            return false;
        }
        return id != null && id.equals(((PlanoAcao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PlanoAcao{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", classificacao=" + getClassificacao() +
            ", mensagemAlterta='" + getMensagemAlterta() + "'" +
            "}";
    }
}
