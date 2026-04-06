package com.taller.erjpa.repository;

import com.taller.erjpa.model.Observacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservacionRepository extends JpaRepository<Observacion, Long> {
}
