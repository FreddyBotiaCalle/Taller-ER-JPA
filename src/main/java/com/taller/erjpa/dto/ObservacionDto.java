package com.taller.erjpa.dto;

import java.time.LocalDate;
import java.util.List;

public class ObservacionDto {

    private Long idObservacion;
    private String observacion;
    private LocalDate fechaRegistroObservacion;
    private Long idEvaluacion;
    private List<Long> idsDocentes;

    public ObservacionDto() {
    }

    public ObservacionDto(Long idObservacion, String observacion, LocalDate fechaRegistroObservacion, Long idEvaluacion, List<Long> idsDocentes) {
        this.idObservacion = idObservacion;
        this.observacion = observacion;
        this.fechaRegistroObservacion = fechaRegistroObservacion;
        this.idEvaluacion = idEvaluacion;
        this.idsDocentes = idsDocentes;
    }

    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFechaRegistroObservacion() {
        return fechaRegistroObservacion;
    }

    public void setFechaRegistroObservacion(LocalDate fechaRegistroObservacion) {
        this.fechaRegistroObservacion = fechaRegistroObservacion;
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public List<Long> getIdsDocentes() {
        return idsDocentes;
    }

    public void setIdsDocentes(List<Long> idsDocentes) {
        this.idsDocentes = idsDocentes;
    }
}
