package com.taller.erjpa.controller;

import com.taller.erjpa.exception.ResourceNotFoundException;
import com.taller.erjpa.dto.ObservacionRequest;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.service.ObservacionService;
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
@RequestMapping("/api/observaciones")
public class ObservacionController {

    private final ObservacionService observacionService;

    public ObservacionController(ObservacionService observacionService) {
        this.observacionService = observacionService;
    }

    @GetMapping
    public ResponseEntity<List<Observacion>> listarTodas() {
        return ResponseEntity.ok(observacionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Observacion> obtenerPorId(@PathVariable @NonNull Long id) {
        Observacion observacion = observacionService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Observacion no encontrada: " + id));
        return ResponseEntity.ok(observacion);
    }

    @PostMapping
    public ResponseEntity<Observacion> crear(@Valid @RequestBody @NonNull ObservacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(observacionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Observacion> actualizar(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull ObservacionRequest request
    ) {
        Observacion actualizada = observacionService.actualizar(id, request)
            .orElseThrow(() -> new ResourceNotFoundException("Observacion no encontrada: " + id));
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @NonNull Long id) {
        if (!observacionService.eliminar(id)) {
            throw new ResourceNotFoundException("Observacion no encontrada: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
