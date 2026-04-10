package com.taller.erjpa;

import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.service.TallerRelacionesService;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TallerConsolaRunner implements CommandLineRunner {

    private final TallerRelacionesService tallerRelacionesService;
    private final Scanner scanner = new Scanner(System.in);

    public TallerConsolaRunner(TallerRelacionesService tallerRelacionesService) {
        this.tallerRelacionesService = tallerRelacionesService;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            String comando = args[0].trim().toLowerCase(Locale.ROOT);
            if (comando.isBlank() || "salir".equals(comando)) {
                System.out.println("Sin ejecucion de metodos del taller.");
                return;
            }
            ejecutarComando(comando);
            return;
        }

        while (true) {
            String comando = leerComandoDesdeMenu();

            if (comando == null || comando.isBlank() || "salir".equals(comando)) {
                System.out.println("Saliendo del menu del taller.");
                break;
            }

            ejecutarComando(comando);
        }
    }

    private String leerComandoDesdeMenu() {
        System.out.println("\n===== MENU METODOS TALLER =====");
        System.out.println("1. crear-formatoa");
        System.out.println("2. crear-observacion");
        System.out.println("3. listar-observaciones");
        System.out.println("4. listar-comite");
        System.out.println("5. consultar-formatos-docente");
        System.out.println("6. historial-formato-por-titulo");
        System.out.println("7. existe-formato-titulo-nativo");
        System.out.println("8. actualizar-estado-formato");
        System.out.println("9. existe-docente-correo-nativo");
        System.out.println("0. salir");
        System.out.print("Selecciona una opcion: ");

        String opcion = scanner.nextLine().trim();

        return switch (opcion) {
            case "1" -> "crear-formatoa";
            case "2" -> "crear-observacion";
            case "3" -> "listar-observaciones";
            case "4" -> "listar-comite";
            case "5" -> "consultar-formatos-docente";
            case "6" -> "historial-formato-por-titulo";
            case "7" -> "existe-formato-titulo-nativo";
            case "8" -> "actualizar-estado-formato";
            case "9" -> "existe-docente-correo-nativo";
            case "0" -> "salir";
            default -> opcion.toLowerCase(Locale.ROOT);
        };
    }

    private void ejecutarComando(String comando) {
        System.out.println("\n=== Ejecutando: " + comando + " ===");

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
                case "historial-formato-por-titulo" ->
                    System.out.println(tallerRelacionesService.consultarHistorialFormatoAPorTitulo("Formato TI-A de prueba"));
                case "existe-formato-titulo-nativo" ->
                    System.out.println(tallerRelacionesService.existeFormatoATituloNativo("Formato TI-A de prueba"));
                case "actualizar-estado-formato" ->
                    System.out.println(tallerRelacionesService.agregarNuevoEstadoPorFormatoA(1L, "En revision"));
                case "existe-docente-correo-nativo" ->
                    System.out.println(tallerRelacionesService.existeDocenteCorreoNativo("ana.perez@correo.com"));
            default -> System.out.println("Comando no reconocido.");
        }

        System.out.println("=== Fin ejecucion ===\n");
    }
}
