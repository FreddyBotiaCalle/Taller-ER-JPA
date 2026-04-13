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
                try {
                    System.out.print("Ingrese ID del docente: ");
                    Long docenteId = Long.parseLong(scanner.nextLine().trim());

                    System.out.print("Ingrese tipo de formato (TIA o PPA): ");
                    String tipoInput = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
                    TallerRelacionesService.ModalidadFormato modalidad =
                            TallerRelacionesService.ModalidadFormato.valueOf(tipoInput);

                    System.out.print("Ingrese titulo: ");
                    String titulo = scanner.nextLine().trim();
                    if (titulo.isBlank()) {
                        System.out.println("Error: el titulo no puede estar vacio.");
                        break;
                    }

                    System.out.print("Ingrese objetivo general: ");
                    String objetivoGeneral = scanner.nextLine().trim();

                    System.out.print("Ingrese objetivos especificos: ");
                    String objetivosEspecificos = scanner.nextLine().trim();

                    FormatoA formato = tallerRelacionesService.crearFormatoA(
                            docenteId,
                            modalidad,
                            titulo,
                            objetivoGeneral,
                            objetivosEspecificos
                    );
                    System.out.println("Formato creado con ID: " + formato.getIdFormA0());

                } catch (NumberFormatException e) {
                    System.out.println("Error: el ID del docente debe ser un numero entero.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: tipo de formato invalido. Use TIA o PPA.");
                } catch (Exception e) {
                    System.out.println("Error al crear formato: " + e.getMessage());
                }
            }
            case "crear-observacion" -> {
                try {
                    System.out.print("Ingrese ID del formato A: ");
                    Long formatoAId = Long.parseLong(scanner.nextLine().trim());

                    System.out.print("Ingrese IDs de docentes separados por coma (ej: 1,2): ");
                    String idsInput = scanner.nextLine().trim();
                    if (idsInput.isBlank()) {
                        System.out.println("Error: debe ingresar al menos un ID de docente.");
                        break;
                    }
                    List<Long> idsDocentes = java.util.Arrays.stream(idsInput.split(","))
                            .map(String::trim)
                            .map(Long::parseLong)
                            .toList();

                    System.out.print("Ingrese comentario: ");
                    String comentario = scanner.nextLine().trim();

                    Observacion observacion = tallerRelacionesService.crearObservacion(
                            formatoAId, idsDocentes, comentario);
                    System.out.println("Observacion creada con ID: " + observacion.getIdObservacion());

                } catch (NumberFormatException e) {
                    System.out.println("Error: los IDs deben ser numeros enteros.");
                } catch (Exception e) {
                    System.out.println("Error al crear observacion: " + e.getMessage());
                }
            }
            case "listar-observaciones" -> {
                try {
                    System.out.print("Ingrese ID del formato A: ");
                    Long formatoAId = Long.parseLong(scanner.nextLine().trim());
                    System.out.println(tallerRelacionesService.listarObservaciones(formatoAId));
                } catch (NumberFormatException e) {
                    System.out.println("Error: el ID debe ser un numero entero.");
                } catch (Exception e) {
                    System.out.println("Error al listar observaciones: " + e.getMessage());
                }
            }
            case "listar-comite" -> {
                try {
                    System.out.println(tallerRelacionesService.listarMiembrosComite());
                } catch (Exception e) {
                    System.out.println("Error al listar comite: " + e.getMessage());
                }
            }
            case "consultar-formatos-docente" -> {
                try {
                    System.out.print("Ingrese ID del docente: ");
                    Long docenteId = Long.parseLong(scanner.nextLine().trim());
                    System.out.println(tallerRelacionesService.consultarFormatosAPorDocente(docenteId));
                } catch (NumberFormatException e) {
                    System.out.println("Error: el ID debe ser un numero entero.");
                } catch (Exception e) {
                    System.out.println("Error al consultar formatos: " + e.getMessage());
                }
            }
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
