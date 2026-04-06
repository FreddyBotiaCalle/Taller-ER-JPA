package com.taller.erjpa.controller;

import com.taller.erjpa.dto.CrearFormatoARequest;
import com.taller.erjpa.dto.CrearObservacionRequest;
import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.model.Observacion;
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

@RestController
@RequestMapping("/api/formatos-a")
public class FormatoAController {

    private final TallerRelacionesService tallerRelacionesService;

    public FormatoAController(TallerRelacionesService tallerRelacionesService) {
        this.tallerRelacionesService = tallerRelacionesService;
    }

    @PostMapping
    public ResponseEntity<FormatoA> crearFormatoA(@Valid @RequestBody @NonNull CrearFormatoARequest request) {
        TallerRelacionesService.ModalidadFormato modalidad =
                TallerRelacionesService.ModalidadFormato.valueOf(request.modalidad().toUpperCase());

        FormatoA creado = tallerRelacionesService.crearFormatoA(
                modalidad,
                request.titulo(),
                request.objetivoGeneral(),
                request.objetivosEspecificos(),
                request.correoDocente(),
                request.nombresDocente(),
                request.apellidosDocente(),
                request.nombreGrupo()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/observaciones")
    public ResponseEntity<Observacion> crearObservacion(@Valid @RequestBody @NonNull CrearObservacionRequest request) {
        Observacion creada = tallerRelacionesService.crearObservacion(
            Objects.requireNonNull(request.idFormatoA(), "idFormatoA es obligatorio"),
                request.idsDocentes(),
                request.observacion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}/detalle-observaciones")
    public ResponseEntity<String> listarObservaciones(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(tallerRelacionesService.listarObservaciones(id));
    }
}
