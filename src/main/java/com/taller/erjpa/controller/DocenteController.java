package com.taller.erjpa.controller;

import com.taller.erjpa.exception.ResourceNotFoundException;
import com.taller.erjpa.model.Docente;
import com.taller.erjpa.service.DocenteService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    public ResponseEntity<List<Docente>> listarTodos() {
        return ResponseEntity.ok(docenteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> obtenerPorId(@PathVariable @NonNull Long id) {
        Docente docente = docenteService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado: " + id));
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    public ResponseEntity<Docente> crear(@Valid @RequestBody @NonNull Docente docente) {
        Docente creado = docenteService.crear(docente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> actualizar(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull Docente docente
    ) {
        Docente actualizado = docenteService.actualizar(id, docente)
            .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado: " + id));
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @NonNull Long id) {
        if (!docenteService.eliminar(id)) {
            throw new ResourceNotFoundException("Docente no encontrado: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
