package com.taller.erjpa.service;

import com.taller.erjpa.dto.ObservacionRequest;
import com.taller.erjpa.model.Docente;
import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.repository.DocenteRepository;
import com.taller.erjpa.repository.EvaluacionRepository;
import com.taller.erjpa.repository.ObservacionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ObservacionService {

    private final ObservacionRepository observacionRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final DocenteRepository docenteRepository;

    public ObservacionService(
            ObservacionRepository observacionRepository,
            EvaluacionRepository evaluacionRepository,
            DocenteRepository docenteRepository
    ) {
        this.observacionRepository = observacionRepository;
        this.evaluacionRepository = evaluacionRepository;
        this.docenteRepository = docenteRepository;
    }

    public List<Observacion> listarTodas() {
        return observacionRepository.findAll();
    }

    public Optional<Observacion> obtenerPorId(@NonNull Long id) {
        return observacionRepository.findById(id);
    }

    public Observacion crear(@NonNull ObservacionRequest request) {
        Evaluacion evaluacion = evaluacionRepository.getReferenceById(
            Objects.requireNonNull(request.idEvaluacion(), "idEvaluacion es obligatorio")
        );
        List<Docente> docentes = new ArrayList<>();
        for (Long idDocente : request.idsDocentes()) {
            docentes.add(docenteRepository.getReferenceById(
                Objects.requireNonNull(idDocente, "idsDocentes contiene un valor nulo")
            ));
        }

        Observacion observacion = new Observacion();
        observacion.setEvaluacion(evaluacion);
        observacion.setObservacion(request.observacion());
        observacion.setFechaRegistroObservacion(
                request.fechaRegistroObservacion() != null ? request.fechaRegistroObservacion() : LocalDate.now()
        );
        observacion.setDocentes(docentes);

        return observacionRepository.save(observacion);
    }

    public Optional<Observacion> actualizar(@NonNull Long id, @NonNull ObservacionRequest request) {
        return observacionRepository.findById(id).map(existente -> {
            existente.setEvaluacion(evaluacionRepository.getReferenceById(
                Objects.requireNonNull(request.idEvaluacion(), "idEvaluacion es obligatorio")
            ));
            existente.setObservacion(request.observacion());
            existente.setFechaRegistroObservacion(
                    request.fechaRegistroObservacion() != null ? request.fechaRegistroObservacion() : existente.getFechaRegistroObservacion()
            );

            List<Docente> docentes = new ArrayList<>();
            for (Long idDocente : request.idsDocentes()) {
            docentes.add(docenteRepository.getReferenceById(
                Objects.requireNonNull(idDocente, "idsDocentes contiene un valor nulo")
            ));
            }
            existente.setDocentes(docentes);

            return observacionRepository.save(existente);
        });
    }

    public boolean eliminar(@NonNull Long id) {
        if (!observacionRepository.existsById(id)) {
            return false;
        }
        observacionRepository.deleteById(id);
        return true;
    }
}
