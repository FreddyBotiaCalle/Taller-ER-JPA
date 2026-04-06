package com.taller.erjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "estados")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long idEstado;

    @Column(name = "estado_actual", nullable = false, length = 50)
    private String estadoActual;

    @Column(name = "fecha_registro_estado")
    private LocalDate fechaRegistroEstado;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_form_a0", nullable = false, unique = true)
    private FormatoA formatoA;

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public LocalDate getFechaRegistroEstado() {
        return fechaRegistroEstado;
    }

    public void setFechaRegistroEstado(LocalDate fechaRegistroEstado) {
        this.fechaRegistroEstado = fechaRegistroEstado;
    }

    public FormatoA getFormatoA() {
        return formatoA;
    }

    public void setFormatoA(FormatoA formatoA) {
        this.formatoA = formatoA;
    }
}
