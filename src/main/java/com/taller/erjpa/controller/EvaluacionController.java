package com.taller.erjpa.controller;

import com.taller.erjpa.dto.EvaluacionRequest;
import com.taller.erjpa.dto.EvaluacionDto;
import com.taller.erjpa.exception.ResourceNotFoundException;
import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.service.EvaluacionService;
import java.util.stream.Collectors;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Evaluaciones", description = "Operaciones sobre evaluaciones de formatos")
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    public EvaluacionController(EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
        public ResponseEntity<?> listarTodas(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
        ) {
        if (page != null && size != null) {
            org.springframework.data.domain.Page<Evaluacion> resultado = evaluacionService.listarPagina(
                org.springframework.data.domain.PageRequest.of(page, size)
            );
            org.springframework.data.domain.Page<EvaluacionDto> dtos = resultado.map(e -> new EvaluacionDto(
                e.getIdEvaluacion(),
                e.getConcepto(),
                e.getFechaRegistroConcepto(),
                e.getNombreCoordinador(),
                e.getFormatoA() != null ? e.getFormatoA().getIdFormA0() : null
            ));
            return ResponseEntity.ok(dtos);
        }

        List<EvaluacionDto> dtos = evaluacionService.listarTodas().stream().map(e -> new EvaluacionDto(
            e.getIdEvaluacion(),
            e.getConcepto(),
            e.getFechaRegistroConcepto(),
            e.getNombreCoordinador(),
            e.getFormatoA() != null ? e.getFormatoA().getIdFormA0() : null
        )).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
        }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionDto> obtenerPorId(@PathVariable @NonNull Long id) {
        Evaluacion evaluacion = evaluacionService.obtenerPorId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada: " + id));
        EvaluacionDto dto = new EvaluacionDto(
                evaluacion.getIdEvaluacion(),
                evaluacion.getConcepto(),
                evaluacion.getFechaRegistroConcepto(),
                evaluacion.getNombreCoordinador(),
                evaluacion.getFormatoA() != null ? evaluacion.getFormatoA().getIdFormA0() : null
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EvaluacionDto> crear(@Valid @RequestBody @NonNull EvaluacionRequest request) {
        Evaluacion creada = evaluacionService.crear(request);
        EvaluacionDto dto = new EvaluacionDto(
                creada.getIdEvaluacion(),
                creada.getConcepto(),
                creada.getFechaRegistroConcepto(),
                creada.getNombreCoordinador(),
                creada.getFormatoA() != null ? creada.getFormatoA().getIdFormA0() : null
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
        public ResponseEntity<EvaluacionDto> actualizar(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull EvaluacionRequest request
    ) {
        Evaluacion actualizada = evaluacionService.actualizar(id, request)
            .orElseThrow(() -> new ResourceNotFoundException("Evaluacion no encontrada: " + id));
        EvaluacionDto dto = new EvaluacionDto(
                actualizada.getIdEvaluacion(),
                actualizada.getConcepto(),
                actualizada.getFechaRegistroConcepto(),
                actualizada.getNombreCoordinador(),
                actualizada.getFormatoA() != null ? actualizada.getFormatoA().getIdFormA0() : null
        );
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable @NonNull Long id) {
        if (!evaluacionService.eliminar(id)) {
            throw new ResourceNotFoundException("Evaluacion no encontrada: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
