package com.taller.erjpa.repository;

import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.model.FormatoA;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

	Optional<Evaluacion> findTopByFormatoAOrderByFechaRegistroConceptoDescIdEvaluacionDesc(FormatoA formatoA);

    @Query("""
	    select e from Evaluacion e
	    join e.formatoA f
	    join f.docente d
	    where e.fechaRegistroConcepto between :fechaInicio and :fechaFin
	      and lower(concat(d.nombresDocente, ' ', d.apellidosDocente)) like concat('%', lower(:parteNombreDocente), '%')
	    order by e.fechaRegistroConcepto desc, e.idEvaluacion desc
	    """)
    List<Evaluacion> buscarPorRangoFechasYNombreDocenteIgnoreCase(
	    @Param("fechaInicio") LocalDate fechaInicio,
	    @Param("fechaFin") LocalDate fechaFin,
	    @Param("parteNombreDocente") String parteNombreDocente
    );
}
