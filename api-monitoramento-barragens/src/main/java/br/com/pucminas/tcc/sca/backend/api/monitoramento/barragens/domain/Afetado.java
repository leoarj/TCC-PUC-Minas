package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Afetado.
 */
@Entity
@Table(name = "afetado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Afetado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 130)
    @Column(name = "email", length = 130, nullable = false)
    private String email;

    @NotNull
    @Size(max = 16)
    @Column(name = "telefone_principal", length = 16, nullable = false)
    private String telefonePrincipal;

    @NotNull
    @Size(max = 16)
    @Column(name = "telefone_reserva", length = 16, nullable = false)
    private String telefoneReserva;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("afetados")
    private Barragem barragem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Afetado nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public Afetado email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public Afetado telefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
        return this;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneReserva() {
        return telefoneReserva;
    }

    public Afetado telefoneReserva(String telefoneReserva) {
        this.telefoneReserva = telefoneReserva;
        return this;
    }

    public void setTelefoneReserva(String telefoneReserva) {
        this.telefoneReserva = telefoneReserva;
    }

    public Barragem getBarragem() {
        return barragem;
    }

    public Afetado barragem(Barragem barragem) {
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
        if (!(o instanceof Afetado)) {
            return false;
        }
        return id != null && id.equals(((Afetado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Afetado{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneReserva='" + getTelefoneReserva() + "'" +
            "}";
    }
}
