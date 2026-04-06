package com.taller.erjpa.controller;

import com.taller.erjpa.service.TallerRelacionesService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
