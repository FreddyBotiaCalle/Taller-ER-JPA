package com.taller.erjpa.repository;

import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.model.FormatoA;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

	Optional<Evaluacion> findTopByFormatoAOrderByFechaRegistroConceptoDescIdEvaluacionDesc(FormatoA formatoA);
}
