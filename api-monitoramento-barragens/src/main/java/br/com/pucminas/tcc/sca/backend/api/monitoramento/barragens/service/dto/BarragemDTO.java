package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

//import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Afetado;
import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.GrauRisco;

/**
 * A DTO for the {@link br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.Barragem} entity.
 */
public class BarragemDTO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1216709017954579884L;

	private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    private String nome;

    @NotNull
    @DecimalMin(value = "10")
    private BigDecimal capacidadeMetrosCubicos;

    private Double latitude;

    private Double longitude;

    @NotNull
    private GrauRisco grauRisco;
    
    private Set<AfetadoDTO> afetados;    

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

    public BigDecimal getCapacidadeMetrosCubicos() {
        return capacidadeMetrosCubicos;
    }

    public void setCapacidadeMetrosCubicos(BigDecimal capacidadeMetrosCubicos) {
        this.capacidadeMetrosCubicos = capacidadeMetrosCubicos;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public GrauRisco getGrauRisco() {
        return grauRisco;
    }

    public void setGrauRisco(GrauRisco grauRisco) {
        this.grauRisco = grauRisco;
    }
    
    public Set<AfetadoDTO> getAfetados() {
		return afetados;
	}

	public void setAfetados(Set<AfetadoDTO> afetadosDTO) {
		this.afetados = afetadosDTO;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BarragemDTO barragemDTO = (BarragemDTO) o;
        if (barragemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), barragemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BarragemDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", capacidadeMetrosCubicos=" + getCapacidadeMetrosCubicos() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", grauRisco='" + getGrauRisco() + "'" +
            ", afetados='" + getAfetados() + "'" +
            "}";
    }
}
