package com.taller.erjpa.dto;

public record EstadoUpdateResponse(
        Long idFormatoA,
        String nuevoEstado,
        String mensaje
) {
}
