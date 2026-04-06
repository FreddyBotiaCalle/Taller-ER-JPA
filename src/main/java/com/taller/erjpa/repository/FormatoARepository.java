package com.taller.erjpa.repository;

import com.taller.erjpa.model.FormatoA;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormatoARepository extends JpaRepository<FormatoA, Long> {

    @Query("""
	    select distinct f from FormatoA f
	    left join fetch f.estado e
	    left join fetch f.evaluaciones ev
	    left join fetch ev.observaciones o
	    left join fetch o.docentes
	    where f.idFormA0 = :idFormatoA
	    """)
    Optional<FormatoA> findDetalleObservaciones(@Param("idFormatoA") Long idFormatoA);

    @Query("""
	    select distinct f from FormatoA f
	    where f.docente.idDocente = :idDocente
	    """)
    List<FormatoA> findByDocenteIdDocente(@Param("idDocente") Long idDocente);
}
