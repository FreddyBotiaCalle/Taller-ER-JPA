package com.taller.erjpa.controller;

import com.taller.erjpa.service.TallerRelacionesService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taller")
public class ConsultaTallerController {

    private final TallerRelacionesService tallerRelacionesService;

    public ConsultaTallerController(TallerRelacionesService tallerRelacionesService) {
        this.tallerRelacionesService = tallerRelacionesService;
    }

    @GetMapping("/comite")
    public ResponseEntity<String> listarComite() {
        return ResponseEntity.ok(tallerRelacionesService.listarMiembrosComite());
    }

    @GetMapping("/docentes/{idDocente}/formatos-a")
    public ResponseEntity<String> consultarFormatosDocente(@PathVariable @NonNull Long idDocente) {
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
    public ResponseEntity<String> actualizarEstadoFormato(
            @PathVariable @NonNull Long idFormatoA,
            @RequestParam @NonNull String nuevoEstado
    ) {
        return ResponseEntity.ok(tallerRelacionesService.agregarNuevoEstadoPorFormatoA(idFormatoA, nuevoEstado));
    }

    @GetMapping("/docentes/existe")
    public ResponseEntity<Boolean> existeDocentePorCorreo(@RequestParam @NonNull String correo) {
        return ResponseEntity.ok(tallerRelacionesService.existeDocenteCorreoNativo(correo));
    }
}
