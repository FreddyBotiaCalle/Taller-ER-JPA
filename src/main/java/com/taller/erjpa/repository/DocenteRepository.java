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

        @Query("""
            select (count(d) > 0)
            from Docente d
            where lower(d.correo) = lower(:correo)
            """)
    boolean existeDocenteCorreoNativo(@Param("correo") String correo);

            @Query(value = "SELECT CASE WHEN COUNT(*)>0 THEN TRUE ELSE FALSE END FROM docentes WHERE LOWER(correo)=LOWER(:correo)", nativeQuery = true)
            boolean existeDocenteCorreoNative(@Param("correo") String correo);

        @Query("""
                        select d from Docente d
                        where lower(d.nombreGrupo) = lower(:nombreGrupo)
                            and lower(d.apellidosDocente) like concat(lower(:patron), '%')
                        order by lower(d.apellidosDocente) asc
                        """)
        List<Docente> buscarPorGrupoYPatronApellidoOrdenado(@Param("nombreGrupo") String nombreGrupo, @Param("patron") String patron);
}
