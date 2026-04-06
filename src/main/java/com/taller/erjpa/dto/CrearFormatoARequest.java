package com.taller.erjpa.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearFormatoARequest(
        @NotBlank String modalidad,
        @NotBlank String titulo,
        String objetivoGeneral,
        String objetivosEspecificos,
        @NotBlank String correoDocente,
        @NotBlank String nombresDocente,
        @NotBlank String apellidosDocente,
        @NotBlank String nombreGrupo
) {
}
