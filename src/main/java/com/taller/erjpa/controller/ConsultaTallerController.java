package com.taller.erjpa.controller;

import com.taller.erjpa.service.TallerRelacionesService;
import org.springframework.http.ResponseEntity;
import com.taller.erjpa.dto.EstadoUpdateResponse;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@Tag(name = "Taller", description = "Consultas y acciones relacionadas con el taller y sus formatos")
@RequestMapping("/api/taller")
public class ConsultaTallerController {

    private final TallerRelacionesService tallerRelacionesService;

    public ConsultaTallerController(TallerRelacionesService tallerRelacionesService) {
        this.tallerRelacionesService = tallerRelacionesService;
    }

    @GetMapping("/comite")
    public ResponseEntity<List<com.taller.erjpa.dto.HistoricoDto>> listarComite() {
        return ResponseEntity.ok(tallerRelacionesService.listarMiembrosComiteDto());
    }

    @GetMapping("/docentes/{idDocente}/formatos-a")
    public ResponseEntity<String> consultarFormatosDocente(@PathVariable @NonNull @jakarta.validation.constraints.Min(1) Long idDocente) {
        return ResponseEntity.ok(tallerRelacionesService.consultarFormatosAPorDocente(idDocente));
    }

    @GetMapping("/formatos-a/historial")
    public ResponseEntity<String> consultarHistorialFormato(@RequestParam @NonNull String titulo) {
        return ResponseEntity.ok(tallerRelacionesService.consultarHistorialFormatoAPorTitulo(titulo));
    }

    @GetMapping("/formatos-a/existe")
    public ResponseEntity<Boolean> existeFormato(@RequestParam @NonNull String titulo) {
        return ResponseEntity.ok(tallerRelacionesService.existeFormatoATituloNativo(titulo));
    }

    @PatchMapping("/formatos-a/{idFormatoA}/estado")
    public ResponseEntity<EstadoUpdateResponse> actualizarEstadoFormato(
            @PathVariable @NonNull Long idFormatoA,
            @RequestParam @NonNull String nuevoEstado
    ) {
        String mensaje = tallerRelacionesService.agregarNuevoEstadoPorFormatoA(idFormatoA, nuevoEstado);
        EstadoUpdateResponse resp = new EstadoUpdateResponse(idFormatoA, nuevoEstado, mensaje);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/docentes/existe")
    public ResponseEntity<Boolean> existeDocentePorCorreo(@RequestParam @NonNull String correo) {
        return ResponseEntity.ok(tallerRelacionesService.existeDocenteCorreoNativo(correo));
    }
}
