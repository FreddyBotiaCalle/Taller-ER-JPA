package com.taller.erjpa.service;

import com.taller.erjpa.dto.DocenteDto;
import com.taller.erjpa.model.Docente;
import com.taller.erjpa.repository.DocenteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class DocenteService {

    private final DocenteRepository docenteRepository;

    public DocenteService(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public List<DocenteDto> listarTodos() {
        return docenteRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<DocenteDto> listar(Pageable pageable) {
        return docenteRepository.findAll(pageable).map(this::toDto);
    }

    public Optional<DocenteDto> obtenerPorId(@NonNull Long id) {
        return docenteRepository.findById(id).map(this::toDto);
    }

    public DocenteDto crear(@NonNull DocenteDto docenteDto) {
        Docente entidad = fromDto(docenteDto);
        Docente creado = docenteRepository.save(entidad);
        return toDto(creado);
    }

    public Optional<DocenteDto> actualizar(@NonNull Long id, @NonNull DocenteDto datos) {
        return docenteRepository.findById(id)
                .map(existente -> {
                    existente.setNombresDocente(datos.getNombresDocente());
                    existente.setApellidosDocente(datos.getApellidosDocente());
                    existente.setNombreGrupo(datos.getNombreGrupo());
                    existente.setCorreo(datos.getCorreo());
                    return toDto(docenteRepository.save(existente));
                });
    }

    public boolean eliminar(@NonNull Long id) {
        if (!docenteRepository.existsById(id)) {
            return false;
        }
        docenteRepository.deleteById(id);
        return true;
    }

    private DocenteDto toDto(Docente d) {
        return new DocenteDto(d.getIdDocente(), d.getNombresDocente(), d.getApellidosDocente(), d.getNombreGrupo(), d.getCorreo());
    }

    private Docente fromDto(DocenteDto dto) {
        Docente d = new Docente();
        d.setNombresDocente(dto.getNombresDocente());
        d.setApellidosDocente(dto.getApellidosDocente());
        d.setNombreGrupo(dto.getNombreGrupo());
        d.setCorreo(dto.getCorreo());
        return d;
    }
}
