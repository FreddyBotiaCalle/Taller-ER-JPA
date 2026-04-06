package com.taller.erjpa.service;

import com.taller.erjpa.model.Docente;
import com.taller.erjpa.repository.DocenteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DocenteService {

    private final DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public List<Docente> listarTodos() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> obtenerPorId(@NonNull Long id) {
        return docenteRepository.findById(id);
    }

    public Docente crear(@NonNull Docente docente) {
        return docenteRepository.save(docente);
    }

    public Optional<Docente> actualizar(@NonNull Long id, @NonNull Docente datos) {
        return docenteRepository.findById(id)
                .map(existente -> {
                    existente.setNombresDocente(datos.getNombresDocente());
                    existente.setApellidosDocente(datos.getApellidosDocente());
                    existente.setNombreGrupo(datos.getNombreGrupo());
                    existente.setCorreo(datos.getCorreo());
                    return docenteRepository.save(existente);
                });
    }

    public boolean eliminar(@NonNull Long id) {
        if (!docenteRepository.existsById(id)) {
            return false;
        }
        docenteRepository.deleteById(id);
        return true;
    }
}
