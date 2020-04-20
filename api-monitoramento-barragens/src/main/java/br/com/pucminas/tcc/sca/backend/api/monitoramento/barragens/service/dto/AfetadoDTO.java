package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado} entity.
 */
public class AfetadoDTO implements Serializable {
        
	/**
	 * 
	 */
	private static final long serialVersionUID = -7488190503639532051L;

	private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    private String nome;

    @NotNull
    @Size(max = 130)
    private String email;

    @NotNull
    @Size(max = 16)
    private String telefonePrincipal;

    @NotNull
    @Size(max = 16)
    private String telefoneReserva;


    private Long barragemId;

    private String barragemNome;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(String telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public String getTelefoneReserva() {
        return telefoneReserva;
    }

    public void setTelefoneReserva(String telefoneReserva) {
        this.telefoneReserva = telefoneReserva;
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

        AfetadoDTO afetadoDTO = (AfetadoDTO) o;
        if (afetadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), afetadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AfetadoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefonePrincipal='" + getTelefonePrincipal() + "'" +
            ", telefoneReserva='" + getTelefoneReserva() + "'" +
            ", barragemId=" + getBarragemId() +
            ", barragemNome='" + getBarragemNome() + "'" +
            "}";
    }
}
