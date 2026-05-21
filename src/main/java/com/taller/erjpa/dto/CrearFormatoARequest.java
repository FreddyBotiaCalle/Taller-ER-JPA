package com.taller.erjpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import com.taller.erjpa.validation.InfinitiveVerb;

public record CrearFormatoARequest(
        Long docenteId,
        @NotBlank String modalidad,
        @NotBlank String titulo,
        String objetivoGeneral,
        @NotEmpty @Size(min = 3, message = "{formato.objetivos.size}") List<@NotBlank @InfinitiveVerb String> objetivosEspecificos
) {
}
