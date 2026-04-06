package com.taller.erjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "formatos_tia")
public class FormatoTia extends FormatoA {

    @Column(name = "nombre_estudiante1", length = 100)
    private String nombreEstudiante1;

    @Column(name = "nombre_estudiante2", length = 100)
    private String nombreEstudiante2;

    public String getNombreEstudiante1() {
        return nombreEstudiante1;
    }

    public void setNombreEstudiante1(String nombreEstudiante1) {
        this.nombreEstudiante1 = nombreEstudiante1;
    }

    public String getNombreEstudiante2() {
        return nombreEstudiante2;
    }

    public void setNombreEstudiante2(String nombreEstudiante2) {
        this.nombreEstudiante2 = nombreEstudiante2;
    }
}
