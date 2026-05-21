package com.taller.erjpa.dto;

import java.time.LocalDate;

public record HistoricoDto(
        Long idHistorico,
        Long idDocente,
        String nombresDocente,
        String apellidosDocente,
        String roleAsignado,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Boolean activo
) {
}
