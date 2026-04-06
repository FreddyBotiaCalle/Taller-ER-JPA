package com.taller.erjpa.repository;

import com.taller.erjpa.model.Historico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {

	List<Historico> findByActivoTrueOrderByFechaInicioDesc();
}
