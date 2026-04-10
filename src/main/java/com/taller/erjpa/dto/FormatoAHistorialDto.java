package com.taller.erjpa.dto;

import java.time.LocalDate;

public record FormatoAHistorialDto(
        Long idFormatoA,
        String tituloFormatoA,
        Long idEvaluacion,
        String conceptoEvaluacion,
        LocalDate fechaEvaluacion,
        Long idObservacion,
        String textoObservacion,
        Long idDocenteObservacion,
        String nombresDocenteObservacion,
        String apellidosDocenteObservacion,
        String correoDocenteObservacion
) {
}
