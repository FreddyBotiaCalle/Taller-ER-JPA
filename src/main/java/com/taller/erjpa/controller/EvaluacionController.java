package com.taller.erjpa.controller;

import com.taller.erjpa.dto.EvaluacionRequest;
import com.taller.erjpa.exception.ResourceNotFoundException;
import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.service.EvaluacionService;
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
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public ResponseEntity<List<Evaluacion>> listarTodas() {
        return ResponseEntity.ok(evaluacionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> obtenerPorId(@PathVariable @NonNull Long id) {
        Evaluacion evaluacion = evaluacionService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada: " + id));
        return ResponseEntity.ok(evaluacion);
    }

    @PostMapping
    public ResponseEntity<Evaluacion> crear(@Valid @RequestBody @NonNull EvaluacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> actualizar(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull EvaluacionRequest request
    ) {
        Evaluacion actualizada = evaluacionService.actualizar(id, request)
            .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada: " + id));
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @NonNull Long id) {
        if (!evaluacionService.eliminar(id)) {
            throw new ResourceNotFoundException("Evaluacion no encontrada: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
