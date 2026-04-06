package com.taller.erjpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evaluaciones")
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Long idEvaluacion;

    @Column(name = "concepto", length = 255)
    private String concepto;

    @Column(name = "fecha_registro_concepto")
    private LocalDate fechaRegistroConcepto;

    @Column(name = "nombre_coordinador", length = 100)
    private String nombreCoordinador;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_form_a0", nullable = false)
    private FormatoA formatoA;

    @JsonIgnore
    @OneToMany(mappedBy = "evaluacion")
    private Set<Observacion> observaciones = new HashSet<>();

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDate getFechaRegistroConcepto() {
        return fechaRegistroConcepto;
    }

    public void setFechaRegistroConcepto(LocalDate fechaRegistroConcepto) {
        this.fechaRegistroConcepto = fechaRegistroConcepto;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }

    public FormatoA getFormatoA() {
        return formatoA;
    }

    public void setFormatoA(FormatoA formatoA) {
        this.formatoA = formatoA;
    }

    public Set<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Set<Observacion> observaciones) {
        this.observaciones = observaciones;
    }
}
