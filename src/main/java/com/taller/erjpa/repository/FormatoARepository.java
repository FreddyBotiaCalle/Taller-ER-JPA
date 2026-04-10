package com.taller.erjpa.repository;

import com.taller.erjpa.dto.FormatoAHistorialDto;
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

	    @Query("""
		    select new com.taller.erjpa.dto.FormatoAHistorialDto(
			f.idFormA0,
			f.titulo,
			e.idEvaluacion,
			e.concepto,
			e.fechaRegistroConcepto,
			o.idObservacion,
			o.observacion,
			d.idDocente,
			d.nombresDocente,
			d.apellidosDocente,
			d.correo
		    )
		    from FormatoA f
		    join f.evaluaciones e
		    left join e.observaciones o
		    left join o.docentes d
		    where lower(f.titulo) = lower(:titulo)
		    order by e.fechaRegistroConcepto desc, e.idEvaluacion desc, o.idObservacion desc
		    """)
	    List<FormatoAHistorialDto> consultarHistorialPorTitulo(@Param("titulo") String titulo);

	    @Query(value = """
		    select case when count(*) > 0 then true else false end
		    from formatosa
		    where lower(titulo) = lower(:titulo)
		    """, nativeQuery = true)
	    boolean existeFormatoATituloNativo(@Param("titulo") String titulo);

	    @Query("""
		    select distinct f from FormatoA f
		    where lower(f.docente.correo) = lower(:correoDocente)
		    """)
	    List<FormatoA> findByCorreoDocenteIgnoreCase(@Param("correoDocente") String correoDocente);

	    @Query("""
		    select distinct f from FormatoA f
		    where lower(concat(f.docente.nombresDocente, ' ', f.docente.apellidosDocente)) like concat('%', lower(:parteNombre), '%')
		    """)
	    List<FormatoA> findByNombreDocenteContainingIgnoreCase(@Param("parteNombre") String parteNombre);
}
