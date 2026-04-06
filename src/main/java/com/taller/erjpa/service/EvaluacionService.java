package com.taller.erjpa.service;

import com.taller.erjpa.dto.EvaluacionRequest;
import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.repository.EvaluacionRepository;
import com.taller.erjpa.repository.FormatoARepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final FormatoARepository formatoARepository;

    public EvaluacionService(EvaluacionRepository evaluacionRepository, FormatoARepository formatoARepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.formatoARepository = formatoARepository;
    }

    public List<Evaluacion> listarTodas() {
        return evaluacionRepository.findAll();
    }

    public Optional<Evaluacion> obtenerPorId(@NonNull Long id) {
        return evaluacionRepository.findById(id);
    }

    public Evaluacion crear(@NonNull EvaluacionRequest request) {
        FormatoA formatoA = formatoARepository.getReferenceById(
            Objects.requireNonNull(request.idFormatoA(), "idFormatoA es obligatorio")
        );
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setFormatoA(formatoA);
        evaluacion.setConcepto(request.concepto());
        evaluacion.setNombreCoordinador(request.nombreCoordinador());
        evaluacion.setFechaRegistroConcepto(
                request.fechaRegistroConcepto() != null ? request.fechaRegistroConcepto() : LocalDate.now()
        );
        return evaluacionRepository.save(evaluacion);
    }

    public Optional<Evaluacion> actualizar(@NonNull Long id, @NonNull EvaluacionRequest request) {
        return evaluacionRepository.findById(id).map(existente -> {
            existente.setConcepto(request.concepto());
            existente.setNombreCoordinador(request.nombreCoordinador());
            existente.setFechaRegistroConcepto(
                    request.fechaRegistroConcepto() != null ? request.fechaRegistroConcepto() : existente.getFechaRegistroConcepto()
            );
            if (!existente.getFormatoA().getIdFormA0().equals(request.idFormatoA())) {
                existente.setFormatoA(formatoARepository.getReferenceById(
                        Objects.requireNonNull(request.idFormatoA(), "idFormatoA es obligatorio")
                ));
            }
            return evaluacionRepository.save(existente);
        });
    }

    public boolean eliminar(@NonNull Long id) {
        if (!evaluacionRepository.existsById(id)) {
            return false;
        }
        evaluacionRepository.deleteById(id);
        return true;
    }
}
