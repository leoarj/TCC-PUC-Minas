package br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.com.pucminas.tcc.sca.backend.api.monitoramento.barragens.domain.enumeration.GrauRisco;

/**
 * A Barragem.
 */
@Entity
@Table(name = "barragem")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Barragem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 60)
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;

    @NotNull
    @DecimalMin(value = "10")
    @Column(name = "capacidade_metros_cubicos", precision = 21, scale = 2, nullable = false)
    private BigDecimal capacidadeMetrosCubicos;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grau_risco", nullable = false)
    private GrauRisco grauRisco;

    @OneToMany(mappedBy = "barragem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sensor> sensors = new HashSet<>();

    @OneToMany(mappedBy = "barragem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Incidente> incidentes = new HashSet<>();

    @OneToMany(mappedBy = "barragem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Afetado> afetados = new HashSet<>();

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

    public Barragem nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getCapacidadeMetrosCubicos() {
        return capacidadeMetrosCubicos;
    }

    public Barragem capacidadeMetrosCubicos(BigDecimal capacidadeMetrosCubicos) {
        this.capacidadeMetrosCubicos = capacidadeMetrosCubicos;
        return this;
    }

    public void setCapacidadeMetrosCubicos(BigDecimal capacidadeMetrosCubicos) {
        this.capacidadeMetrosCubicos = capacidadeMetrosCubicos;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Barragem latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Barragem longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public GrauRisco getGrauRisco() {
        return grauRisco;
    }

    public Barragem grauRisco(GrauRisco grauRisco) {
        this.grauRisco = grauRisco;
        return this;
    }

    public void setGrauRisco(GrauRisco grauRisco) {
        this.grauRisco = grauRisco;
    }

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public Barragem sensors(Set<Sensor> sensors) {
        this.sensors = sensors;
        return this;
    }

    public Barragem addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        sensor.setBarragem(this);
        return this;
    }

    public Barragem removeSensor(Sensor sensor) {
        this.sensors.remove(sensor);
        sensor.setBarragem(null);
        return this;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Set<Incidente> getIncidentes() {
        return incidentes;
    }

    public Barragem incidentes(Set<Incidente> incidentes) {
        this.incidentes = incidentes;
        return this;
    }

    public Barragem addIncidente(Incidente incidente) {
        this.incidentes.add(incidente);
        incidente.setBarragem(this);
        return this;
    }

    public Barragem removeIncidente(Incidente incidente) {
        this.incidentes.remove(incidente);
        incidente.setBarragem(null);
        return this;
    }

    public void setIncidentes(Set<Incidente> incidentes) {
        this.incidentes = incidentes;
    }

    public Set<Afetado> getAfetados() {
        return afetados;
    }

    public Barragem afetados(Set<Afetado> afetados) {
        this.afetados = afetados;
        return this;
    }

    public Barragem addAfetado(Afetado afetado) {
        this.afetados.add(afetado);
        afetado.setBarragem(this);
        return this;
    }

    public Barragem removeAfetado(Afetado afetado) {
        this.afetados.remove(afetado);
        afetado.setBarragem(null);
        return this;
    }

    public void setAfetados(Set<Afetado> afetados) {
        this.afetados = afetados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Barragem)) {
            return false;
        }
        return id != null && id.equals(((Barragem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Barragem{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", capacidadeMetrosCubicos=" + getCapacidadeMetrosCubicos() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", grauRisco='" + getGrauRisco() + "'" +
            "}";
    }
}
