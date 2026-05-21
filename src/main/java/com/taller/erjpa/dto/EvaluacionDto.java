package com.taller.erjpa.dto;

import java.time.LocalDate;

public record EvaluacionDto(
        Long idEvaluacion,
        String concepto,
        LocalDate fechaRegistroConcepto,
        String nombreCoordinador,
        Long idFormatoA
) {
}
