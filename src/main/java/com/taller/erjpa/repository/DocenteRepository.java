package com.taller.erjpa.repository;

import com.taller.erjpa.model.Docente;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByCorreo(String correo);

    Optional<Docente> findByIdDocente(Long idDocente);

    @EntityGraph(attributePaths = "formatosA")
    Optional<Docente> findWithFormatosAByIdDocente(Long idDocente);
}
