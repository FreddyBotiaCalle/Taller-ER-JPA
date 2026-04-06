package com.taller.erjpa.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EvaluacionRequest(
        String concepto,
        LocalDate fechaRegistroConcepto,
        String nombreCoordinador,
        @NotNull Long idFormatoA
) {
}
