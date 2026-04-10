package com.taller.erjpa.repository;

import com.taller.erjpa.model.Docente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByCorreo(String correo);

    Optional<Docente> findByIdDocente(Long idDocente);

    @EntityGraph(attributePaths = "formatosA")
    Optional<Docente> findWithFormatosAByIdDocente(Long idDocente);

    @Query(value = """
            select case when count(*) > 0 then true else false end
            from docentes
            where lower(correo) = lower(:correo)
            """, nativeQuery = true)
    boolean existeDocenteCorreoNativo(@Param("correo") String correo);

        @Query("""
                        select d from Docente d
                        where lower(d.nombreGrupo) = lower(:nombreGrupo)
                            and lower(d.apellidosDocente) like concat(lower(:patron), '%')
                        order by lower(d.apellidosDocente) asc
                        """)
        List<Docente> buscarPorGrupoYPatronApellidoOrdenado(@Param("nombreGrupo") String nombreGrupo, @Param("patron") String patron);
}
