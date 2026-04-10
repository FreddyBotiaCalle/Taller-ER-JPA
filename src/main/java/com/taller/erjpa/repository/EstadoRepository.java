package com.taller.erjpa.repository;

import com.taller.erjpa.model.Estado;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("""
			update Estado e
			set e.estadoActual = :nuevoEstado,
				e.fechaRegistroEstado = CURRENT_DATE
			where e.formatoA.idFormA0 = :idFormatoA
			""")
	int agregarNuevoEstadoPorFormatoA(
			@Param("idFormatoA") Long idFormatoA,
			@Param("nuevoEstado") String nuevoEstado
	);
}
