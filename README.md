# Taller ER-JPA FIET

Implementacion del taller para gestionar formatos de trabajo de grado en FIET, cumpliendo relaciones JPA, herencia, cascadas y metodos transaccionales de prueba por consola.

## Requisitos

- Java 21
- Maven 3.9+
- MySQL 8+

## Ejecutar

1. Crea/inicia tu servidor MySQL.
2. Ajusta las credenciales en `src/main/resources/application-dev.properties`.
3. Ejecuta:

```bash
mvn spring-boot:run
```

La API quedara disponible en `http://localhost:8080/api/docentes`.

## Relacionamiento implementado

- Herencia: `FormatoA` (base abstracta) con `FormatoTia` y `FormatoPpa` usando `InheritanceType.JOINED`
- OneToOne: `FormatoA` -> `Estado` (FK unica)
- OneToMany / ManyToOne: `Docente` -> `FormatoA`, `FormatoA` -> `Evaluacion`, `Evaluacion` -> `Observacion`
- ManyToMany: `Observacion` <-> `Docente` mediante tabla `observacion_docentes`
- Cascadas: `PERSIST` en `FormatoA` hacia `Docente`, y `PERSIST/REMOVE` en `FormatoA` hacia `Estado` y `Evaluacion`

## Metodos del taller por consola

Se ejecutan pasando el comando al arrancar la aplicacion:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=crear-formatoa
mvn spring-boot:run -Dspring-boot.run.arguments=crear-observacion
mvn spring-boot:run -Dspring-boot.run.arguments=listar-observaciones
mvn spring-boot:run -Dspring-boot.run.arguments=listar-comite
mvn spring-boot:run -Dspring-boot.run.arguments=consultar-formatos-docente
```

Todos los metodos estan en `TallerRelacionesService` y usan `@Transactional(readOnly = true/false)`.

## Carga automatica

`src/main/resources/data.sql` importa docentes, roles e historicos de forma automatica al iniciar en perfil `dev`.

## Endpoints de ejemplo

- `GET /api/docentes`
- `GET /api/docentes/{id}`
- `POST /api/docentes`
- `PUT /api/docentes/{id}`
- `DELETE /api/docentes/{id}`

## Body JSON para crear/actualizar

```json
{
  "nombresDocente": "Ana",
  "apellidosDocente": "Perez",
  "nombreGrupo": "G1",
  "correo": "ana.perez@correo.com"
}
```
