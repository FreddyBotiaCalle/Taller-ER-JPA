package com.taller.erjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "formatos_ppa")
public class FormatoPpa extends FormatoA {

    @Column(name = "nombre_asesor", length = 100)
    private String nombreAsesor;

    @Column(name = "nombre_estudiante1", length = 100)
    private String nombreEstudiante1;

    @Column(name = "ruta_carta_aceptacion", length = 255)
    private String rutaCartaAceptacion;

    public String getNombreAsesor() {
        return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor) {
        this.nombreAsesor = nombreAsesor;
    }

    public String getNombreEstudiante1() {
        return nombreEstudiante1;
    }

    public void setNombreEstudiante1(String nombreEstudiante1) {
        this.nombreEstudiante1 = nombreEstudiante1;
    }

    public String getRutaCartaAceptacion() {
        return rutaCartaAceptacion;
    }

    public void setRutaCartaAceptacion(String rutaCartaAceptacion) {
        this.rutaCartaAceptacion = rutaCartaAceptacion;
    }
}
