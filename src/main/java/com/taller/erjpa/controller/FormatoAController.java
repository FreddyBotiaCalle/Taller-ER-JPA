package com.taller.erjpa.controller;

import com.taller.erjpa.dto.CrearFormatoARequest;
import com.taller.erjpa.dto.CrearObservacionRequest;
import com.taller.erjpa.dto.FormatoADto;
import com.taller.erjpa.dto.ObservacionDto;
import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.model.Docente;
import java.util.stream.Collectors;
import java.util.List;
import com.taller.erjpa.service.TallerRelacionesService;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Formatos A", description = "Gestión de formatos tipo A (TIA / PPA)")
@RequestMapping("/api/formatos-a")
public class FormatoAController {

    private final TallerRelacionesService tallerRelacionesService;

    public FormatoAController(TallerRelacionesService tallerRelacionesService) {
        this.tallerRelacionesService = tallerRelacionesService;
    }

    @PostMapping
    public ResponseEntity<FormatoADto> crearFormatoA(@Valid @RequestBody @NonNull CrearFormatoARequest request) {
        TallerRelacionesService.ModalidadFormato modalidad =
                TallerRelacionesService.ModalidadFormato.valueOf(request.modalidad().toUpperCase());

        FormatoA creado = tallerRelacionesService.crearFormatoA(
            request.docenteId(),
            modalidad,
            request.titulo(),
            request.objetivoGeneral(),
            request.objetivosEspecificos()
        );

        FormatoADto dto = new FormatoADto(
            creado.getIdFormA0(),
            creado.getTitulo(),
            creado.getObjetivoGeneral(),
            creado.getObjetivosEspecificos(),
            creado.getDocente() != null ? creado.getDocente().getIdDocente() : null,
            creado.getEstado() != null ? creado.getEstado().getEstadoActual() : null,
            creado.getEstado() != null ? creado.getEstado().getFechaRegistroEstado() : null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/observaciones")
    public ResponseEntity<ObservacionDto> crearObservacion(@Valid @RequestBody @NonNull CrearObservacionRequest request) {
        Observacion creada = tallerRelacionesService.crearObservacion(
            Objects.requireNonNull(request.idFormatoA(), "idFormatoA es obligatorio"),
                request.idsDocentes(),
                request.observacion()
        );

        List<Long> idsDocentes = creada.getDocentes() == null ? List.of() : creada.getDocentes().stream()
                .map(Docente::getIdDocente).collect(Collectors.toList());

        ObservacionDto dto = new ObservacionDto(
                creada.getIdObservacion(),
                creada.getObservacion(),
                creada.getFechaRegistroObservacion(),
                creada.getEvaluacion() != null ? creada.getEvaluacion().getIdEvaluacion() : null,
                idsDocentes
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}/detalle-observaciones")
    public ResponseEntity<String> listarObservaciones(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(tallerRelacionesService.listarObservaciones(id));
    }
}
