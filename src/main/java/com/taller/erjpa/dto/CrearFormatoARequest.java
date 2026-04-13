package com.taller.erjpa.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearFormatoARequest(
        Long docenteId,
        @NotBlank String modalidad,
        @NotBlank String titulo,
        String objetivoGeneral,
        String objetivosEspecificos
) {
}
