package com.taller.erjpa;

import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.service.TallerRelacionesService;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TallerConsolaRunner implements CommandLineRunner {

    private final TallerRelacionesService tallerRelacionesService;

    public TallerConsolaRunner(TallerRelacionesService tallerRelacionesService) {
        this.tallerRelacionesService = tallerRelacionesService;
    }

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            return;
        }

        String comando = args[0].trim().toLowerCase();

        switch (comando) {
            case "crear-formatoa" -> {
                FormatoA formato = tallerRelacionesService.crearFormatoA(
                        TallerRelacionesService.ModalidadFormato.TIA,
                        "Formato TI-A de prueba",
                        "Objetivo general de ejemplo",
                        "Objetivos especificos de ejemplo",
                        "ana.perez@correo.com",
                        "Ana",
                        "Perez",
                        "G1"
                );
                System.out.println("Formato A creado con ID: " + formato.getIdFormA0());
            }
            case "crear-observacion" -> {
                Observacion observacion = tallerRelacionesService.crearObservacion(
                        1L,
                        List.of(1L, 2L),
                        "Se recomienda ajustar el alcance del trabajo."
                );
                System.out.println("Observacion creada con ID: " + observacion.getIdObservacion());
            }
            case "listar-observaciones" -> System.out.println(tallerRelacionesService.listarObservaciones(1L));
            case "listar-comite" -> System.out.println(tallerRelacionesService.listarMiembrosComite());
            case "consultar-formatos-docente" -> System.out.println(tallerRelacionesService.consultarFormatosAPorDocente(1L));
            default -> System.out.println("Comando no reconocido.");
        }
    }
}
