package com.taller.erjpa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CrearObservacionRequest(
        @NotNull Long idFormatoA,
        @NotEmpty List<Long> idsDocentes,
        @NotBlank String observacion
) {
}
