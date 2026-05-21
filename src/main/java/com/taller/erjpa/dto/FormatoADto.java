package com.taller.erjpa.dto;

import java.time.LocalDate;

public record FormatoADto(
        Long idFormA0,
        String titulo,
        String objetivoGeneral,
        String objetivosEspecificos,
        Long docenteId,
        String estadoActual,
        LocalDate fechaRegistroEstado
) {
}
