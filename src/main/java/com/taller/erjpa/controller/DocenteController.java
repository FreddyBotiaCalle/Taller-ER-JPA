package com.taller.erjpa.controller;

import com.taller.erjpa.dto.DocenteDto;
import com.taller.erjpa.exception.ResourceNotFoundException;
import com.taller.erjpa.service.DocenteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<?> listarTodos(
            @org.springframework.web.bind.annotation.RequestParam(required = false) Integer page,
            @org.springframework.web.bind.annotation.RequestParam(required = false) Integer size
    ) {
        if (page == null || size == null) {
            return ResponseEntity.ok(docenteService.listarTodos());
        }
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Page<DocenteDto> resultado = docenteService.listar(pageable);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocenteDto> obtenerPorId(@PathVariable @NonNull Long id) {
        DocenteDto docente = docenteService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado: " + id));
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    public ResponseEntity<DocenteDto> crear(@Valid @RequestBody @NonNull DocenteDto docente) {
        DocenteDto creado = docenteService.crear(docente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteDto> actualizar(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull DocenteDto docente
    ) {
        DocenteDto actualizado = docenteService.actualizar(id, docente)
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
