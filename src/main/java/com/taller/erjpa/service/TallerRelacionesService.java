package com.taller.erjpa.service;

import com.taller.erjpa.dto.FormatoAHistorialDto;
import com.taller.erjpa.model.Docente;
import com.taller.erjpa.model.Estado;
import com.taller.erjpa.model.Evaluacion;
import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.model.FormatoPpa;
import com.taller.erjpa.model.FormatoTia;
import com.taller.erjpa.model.Historico;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.exception.ResourceNotFoundException;
import com.taller.erjpa.repository.DocenteRepository;
import com.taller.erjpa.repository.EvaluacionRepository;
import com.taller.erjpa.repository.EstadoRepository;
import com.taller.erjpa.repository.FormatoARepository;
import com.taller.erjpa.repository.HistoricoRepository;
import com.taller.erjpa.repository.ObservacionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TallerRelacionesService {

    public enum ModalidadFormato {
        TIA,
        PPA
    }

    private final DocenteRepository docenteRepository;
    private final FormatoARepository formatoARepository;
    private final EvaluacionRepository evaluacionRepository;
    private final EstadoRepository estadoRepository;
    private final ObservacionRepository observacionRepository;
    private final HistoricoRepository historicoRepository;

    public TallerRelacionesService(
            DocenteRepository docenteRepository,
            FormatoARepository formatoARepository,
            EvaluacionRepository evaluacionRepository,
            EstadoRepository estadoRepository,
            ObservacionRepository observacionRepository,
            HistoricoRepository historicoRepository
    ) {
        this.docenteRepository = docenteRepository;
        this.formatoARepository = formatoARepository;
        this.evaluacionRepository = evaluacionRepository;
        this.estadoRepository = estadoRepository;
        this.observacionRepository = observacionRepository;
        this.historicoRepository = historicoRepository;
    }

    @Transactional(readOnly = true)
    public String consultarHistorialFormatoAPorTitulo(@NonNull String titulo) {
        List<FormatoAHistorialDto> historial = formatoARepository.consultarHistorialPorTitulo(titulo);
        if (historial.isEmpty()) {
            throw new ResourceNotFoundException("No se encontro historial para el titulo: " + titulo);
        }

        StringBuilder salida = new StringBuilder("Historial del formato: ");
        salida.append(titulo).append("\n");
        for (FormatoAHistorialDto fila : historial) {
            salida.append("Formato #").append(fila.idFormatoA())
                    .append(" | Eval #").append(fila.idEvaluacion())
                    .append(" | Concepto: ").append(fila.conceptoEvaluacion())
                    .append(" | Fecha eval: ").append(fila.fechaEvaluacion())
                    .append(" | Obs #").append(fila.idObservacion())
                    .append(" | Texto: ").append(fila.textoObservacion())
                    .append(" | Docente: ")
                    .append(fila.nombresDocenteObservacion()).append(" ")
                    .append(fila.apellidosDocenteObservacion())
                    .append(" (ID ").append(fila.idDocenteObservacion()).append(")")
                    .append("\n");
        }
        return salida.toString();
    }

    @Transactional(readOnly = true)
    public boolean existeFormatoATituloNativo(@NonNull String titulo) {
        return formatoARepository.existeFormatoATituloNativo(titulo);
    }

    @Transactional(readOnly = false)
    public String agregarNuevoEstadoPorFormatoA(@NonNull Long idFormatoA, @NonNull String nuevoEstado) {
        int actualizados = estadoRepository.agregarNuevoEstadoPorFormatoA(idFormatoA, nuevoEstado);
        if (actualizados == 0) {
            throw new ResourceNotFoundException("No existe estado asociado al formato A con id " + idFormatoA);
        }
        return "Estado actualizado para formato A " + idFormatoA + " -> " + nuevoEstado;
    }

    @Transactional(readOnly = true)
    public boolean existeDocenteCorreoNativo(@NonNull String correo) {
        return docenteRepository.existeDocenteCorreoNativo(correo);
    }

    /**
     * Crea un FormatoA (TIA o PPA) para un docente YA EXISTENTE.
     * - Usa getReferenceById para evitar consulta innecesaria.
     * - NO crea docente nuevo.
     * - Estado inicial persistido en cascada (CascadeType.PERSIST).
     */
    @Transactional
    public FormatoA crearFormatoA(
            @NonNull Long docenteId,
            ModalidadFormato modalidad,
            String titulo,
            String objetivoGeneral,
            String objetivosEspecificos
    ) {
        // getReferenceById: proxy del docente existente, sin consulta inmediata
        Docente docente = docenteRepository.getReferenceById(docenteId);

        FormatoA formato = modalidad == ModalidadFormato.PPA ? new FormatoPpa() : new FormatoTia();
        formato.setTitulo(titulo);
        formato.setObjetivoGeneral(objetivoGeneral);
        formato.setObjetivosEspecificos(objetivosEspecificos);
        formato.setDocente(docente);

        // Estado inicial: se persiste en cascada desde FormatoA (CascadeType.PERSIST)
        // setEstado mantiene consistencia bidireccional (estado.setFormatoA)
        Estado estadoInicial = new Estado();
        estadoInicial.setEstadoActual("En formulacion");
        estadoInicial.setFechaRegistroEstado(LocalDate.now());
        formato.setEstado(estadoInicial);

        // Solo guardamos FormatoA; Estado se persiste en cascada
        return formatoARepository.save(formato);
    }

    @Transactional(readOnly = false)
    public Observacion crearObservacion(@NonNull Long idFormatoA, List<Long> idsDocentes, String textoObservacion) {
        FormatoA formato = formatoARepository.getReferenceById(idFormatoA);

        Evaluacion evaluacion = evaluacionRepository
                .findTopByFormatoAOrderByFechaRegistroConceptoDescIdEvaluacionDesc(formato)
                .map(ultima -> {
                    if (!"Por corregir".equalsIgnoreCase(ultima.getConcepto())) {
                        throw new IllegalStateException(
                                "La ultima evaluacion del formato debe estar en concepto 'Por corregir'."
                        );
                    }
                    return ultima;
                })
                .orElseGet(() -> {
                    Evaluacion nueva = new Evaluacion();
                    nueva.setConcepto("Sin definir");
                    nueva.setFechaRegistroConcepto(LocalDate.now());
                    nueva.setNombreCoordinador("Pendiente");
                    nueva.setFormatoA(formato);
                    return evaluacionRepository.save(nueva);
                });

        Evaluacion evaluacionRef = evaluacionRepository.getReferenceById(
            Objects.requireNonNull(evaluacion.getIdEvaluacion(), "La evaluacion debe tener ID")
        );

        List<Docente> docentes = new ArrayList<>();
        for (Long idDocente : idsDocentes) {
            docentes.add(docenteRepository.getReferenceById(
                    Objects.requireNonNull(idDocente, "La lista de docentes contiene un ID nulo")
            ));
        }

        Observacion observacion = new Observacion();
        observacion.setEvaluacion(evaluacionRef);
        observacion.setObservacion(textoObservacion);
        observacion.setFechaRegistroObservacion(LocalDate.now());
        observacion.setDocentes(docentes);

        return observacionRepository.save(observacion);
    }

    @Transactional(readOnly = true)
    public String listarObservaciones(@NonNull Long idFormatoA) {
        FormatoA formato = formatoARepository.findDetalleObservaciones(idFormatoA)
                .orElseThrow(() -> new ResourceNotFoundException("Formato A no encontrado: " + idFormatoA));

        StringBuilder salida = new StringBuilder();
        salida.append("FormatoA #").append(formato.getIdFormA0())
                .append(" - ").append(formato.getTitulo()).append("\n");

        if (formato.getEstado() != null) {
            salida.append("Estado: ").append(formato.getEstado().getEstadoActual())
                    .append(" (fecha ").append(formato.getEstado().getFechaRegistroEstado()).append(")\n");
        }

        List<Evaluacion> evaluacionesOrdenadas = new ArrayList<>(formato.getEvaluaciones());
        evaluacionesOrdenadas.sort(Comparator.comparing(Evaluacion::getIdEvaluacion));

        for (Evaluacion evaluacion : evaluacionesOrdenadas) {
            salida.append("Evaluacion #").append(evaluacion.getIdEvaluacion())
                    .append(" - concepto: ").append(evaluacion.getConcepto()).append("\n");
            for (Observacion observacion : evaluacion.getObservaciones()) {
                salida.append("  Observacion #").append(observacion.getIdObservacion())
                        .append(": ").append(observacion.getObservacion()).append("\n");
                for (Docente docente : observacion.getDocentes()) {
                    salida.append("    Docente: ")
                            .append(docente.getNombresDocente()).append(" ")
                            .append(docente.getApellidosDocente())
                            .append(" (ID ").append(docente.getIdDocente()).append(")\n");
                }
            }
        }

        return salida.toString();
    }

    @Transactional(readOnly = true)
    public String listarMiembrosComite() {
        List<Historico> historicos = historicoRepository.findByActivoTrueOrderByFechaInicioDesc();
        StringBuilder salida = new StringBuilder("Miembros del comite\n");

        for (Historico historico : historicos) {
            salida.append("- ")
                    .append(historico.getDocente().getNombresDocente()).append(" ")
                    .append(historico.getDocente().getApellidosDocente())
                    .append(" | Rol: ").append(historico.getRol().getRoleAsignado())
                    .append(" | Inicio: ").append(historico.getFechaInicio())
                    .append(" | Fin: ").append(historico.getFechaFin())
                    .append("\n");
        }

        return salida.toString();
    }

    @Transactional(readOnly = true)
    public String consultarFormatosAPorDocente(@NonNull Long idDocente) {
        Docente docente = docenteRepository.findWithFormatosAByIdDocente(idDocente)
                .orElseThrow(() -> new ResourceNotFoundException("Docente no encontrado: " + idDocente));

        StringBuilder salida = new StringBuilder();
        salida.append("Docente: ").append(docente.getNombresDocente()).append(" ")
                .append(docente.getApellidosDocente()).append("\n");

        for (FormatoA formato : docente.getFormatosA()) {
            salida.append("FormatoA #").append(formato.getIdFormA0())
                    .append(" - ").append(formato.getTitulo()).append("\n");

            for (Evaluacion evaluacion : formato.getEvaluaciones()) {
                salida.append("  Evaluacion #").append(evaluacion.getIdEvaluacion())
                        .append(" - ").append(evaluacion.getConcepto()).append("\n");
                for (Observacion observacion : evaluacion.getObservaciones()) {
                    salida.append("    Observacion #").append(observacion.getIdObservacion())
                            .append(": ").append(observacion.getObservacion()).append("\n");
                }
            }
        }

        return salida.toString();
    }
}
