package com.taller.erjpa;

import com.taller.erjpa.model.FormatoA;
import com.taller.erjpa.model.Observacion;
import com.taller.erjpa.service.TallerRelacionesService;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
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

            try {
                ejecutarComando(comando);
            } catch (Exception ex) {
                System.err.println("[ERROR] La ejecucion de la opcion fallo: " + ex.getMessage());
                System.err.println("El menu continuara activo. Elige otra opcion o 0 para salir.\n");
            }
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
        System.out.println("7. validar-existencia-formato-por-titulo");
        System.out.println("8. actualizar-estado-formato");
        System.out.println("9. validar-existencia-docente-por-correo");
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
                Long docenteId = leerLong("Id del docente: ");
                TallerRelacionesService.ModalidadFormato modalidad = leerModalidadFormato();
                String titulo = leerTexto("Titulo del formato: ");
                String objetivoGeneral = leerTexto("Objetivo general: ");
                    String objetivosEspecificos = leerTexto("Objetivos especificos (separe por coma): ");
                    java.util.List<String> objetivosList = java.util.Arrays.stream(objetivosEspecificos.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .toList();

                    FormatoA formato = tallerRelacionesService.crearFormatoA(
                        docenteId,
                        modalidad,
                        titulo,
                        objetivoGeneral,
                        objetivosList
                    );
                System.out.println("Formato A creado con ID: " + formato.getIdFormA0());
            }
            case "crear-observacion" -> {
                Long idFormatoA = leerLong("Id del formato A: ");
                List<Long> idsDocentes = leerListaLongs("Ids de docentes separados por coma: ");
                String textoObservacion = leerTexto("Texto de la observacion: ");

                Observacion observacion = tallerRelacionesService.crearObservacion(
                idFormatoA,
                idsDocentes,
                textoObservacion
                );
                System.out.println("Observacion creada con ID: " + observacion.getIdObservacion());
            }
            case "listar-observaciones" -> {
                Long idFormatoA = leerLong("Id del formato A: ");
                System.out.println(tallerRelacionesService.listarObservaciones(idFormatoA));
            }
            case "listar-comite" -> System.out.println(tallerRelacionesService.listarMiembrosComite());
            case "consultar-formatos-docente" -> {
                Long docenteId = leerLong("Id del docente: ");
                System.out.println(tallerRelacionesService.consultarFormatosAPorDocente(docenteId));
            }
            case "historial-formato-por-titulo" -> {
                String titulo = leerTexto("Titulo del formato A: ");
                System.out.println(tallerRelacionesService.consultarHistorialFormatoAPorTitulo(titulo));
            }
            case "existe-formato-titulo-nativo" -> {
                String titulo = leerTexto("Titulo del formato A: ");
                boolean existe = tallerRelacionesService.existeFormatoATituloNativo(titulo);
                if (existe) {
                    System.out.println("Si existe un Formato A con ese titulo.");
                } else {
                    System.out.println("No existe un Formato A con ese titulo.");
                }
            }
            case "actualizar-estado-formato" -> {
                Long idFormatoA = leerLong("Id del formato A: ");
                String nuevoEstado = leerTexto("Nuevo estado: ");
                System.out.println(tallerRelacionesService.agregarNuevoEstadoPorFormatoA(idFormatoA, nuevoEstado));
            }
            case "existe-docente-correo-nativo" -> {
                String correo = leerTexto("Correo del docente: ");
                boolean existe = tallerRelacionesService.existeDocenteCorreoNativo(correo);
                if (existe) {
                    System.out.println("Si existe un docente registrado con ese correo.");
                } else {
                    System.out.println("No existe un docente registrado con ese correo.");
                }
            }
            default -> System.out.println("Comando no reconocido.");
        }

        System.out.println("=== Fin ejecucion ===\n");
    }

    private TallerRelacionesService.ModalidadFormato leerModalidadFormato() {
        System.out.println("Modalidad del formato:");
        System.out.println("1. TIA");
        System.out.println("2. PPA");
        System.out.print("Selecciona una opcion: ");
        String opcion = scanner.nextLine().trim();
        return "2".equals(opcion) ? TallerRelacionesService.ModalidadFormato.PPA : TallerRelacionesService.ModalidadFormato.TIA;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Valor invalido, intenta de nuevo.");
            }
        }
    }

    private List<Long> leerListaLongs(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String entrada = scanner.nextLine().trim();
                if (entrada.isBlank()) {
                    return new ArrayList<>();
                }
                return Arrays.stream(entrada.split(","))
                        .map(String::trim)
                        .filter(texto -> !texto.isBlank())
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
            } catch (NumberFormatException ex) {
                System.out.println("Uno de los valores no es valido, intenta de nuevo.");
            }
        }
    }
}
