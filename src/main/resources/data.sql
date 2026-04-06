INSERT IGNORE INTO docentes (id_docente, nombres_docente, apellidos_docente, nombre_grupo, correo) VALUES
(1, 'Ana', 'Perez', 'G1', 'ana.perez@correo.com'),
(2, 'Luis', 'Gomez', 'G2', 'luis.gomez@correo.com'),
(3, 'Carlos', 'Ramirez', 'G3', 'carlos.ramirez@correo.com'),
(4, 'Diana', 'Torres', 'G4', 'diana.torres@correo.com'),
(5, 'Jorge', 'Becerra', 'G2', 'jorge.becerra@correo.com'),
(6, 'Paola', 'Quintero', 'G1', 'paola.quintero@correo.com');

INSERT IGNORE INTO roles (id_rol, role_asignado) VALUES
(1, 'Director de trabajo de grado'),
(2, 'Miembro de comite'),
(3, 'Coordinador de programa');

INSERT IGNORE INTO historicos (id_historico, idfk_docente, idfk_rol, activo, fecha_inicio, fecha_fin) VALUES
(1, 1, 1, 1, '2026-01-15', NULL),
(2, 2, 2, 1, '2026-02-01', NULL),
(3, 3, 2, 1, '2026-02-10', NULL),
(4, 4, 3, 1, '2026-01-20', NULL),
(5, 5, 1, 0, '2025-03-01', '2026-01-10'),
(6, 6, 2, 1, '2026-03-05', NULL);

INSERT IGNORE INTO formatosa (id_form_a0, titulo, objetivo_general, objetivos_especificos, idfk_docente) VALUES
(1, 'Modelo de deteccion temprana de fallas en redes IoT',
 'Disenar un sistema de monitoreo inteligente para laboratorios FIET.',
 'Definir arquitectura; construir prototipo; validar precision.',
 1),
(2, 'Plataforma web para seguimiento de practicas profesionales',
 'Centralizar el seguimiento de estudiantes en practica empresarial.',
 'Disenar modulo de seguimiento; generar reportes; evaluar usabilidad.',
 2),
(3, 'Asistente IA para apoyo en formulacion de anteproyectos',
 'Apoyar la construccion de propuestas de grado con tecnicas de IA.',
 'Construir base de prompts; integrar evaluacion automatica; medir calidad.',
 3),
(4, 'Sistema de telemetria para estaciones meteorologicas de bajo costo',
 'Recolectar y visualizar variables ambientales en tiempo real.',
 'Implementar nodos sensores; API de datos; tablero de visualizacion.',
 4),
(5, 'Aplicacion movil para trazabilidad de mantenimientos electricos',
 'Optimizar el control de actividades de mantenimiento en campus.',
 'Disenar app movil; integrar backend; evaluar tiempos de respuesta.',
 1),
(6, 'Repositorio digital de evidencias para trabajo de grado',
 'Consolidar documentos y evidencias de proyectos de grado.',
 'Gestion documental; control de versiones; politicas de acceso.',
 6);

INSERT IGNORE INTO formatos_tia (id_form_a0, nombre_estudiante1, nombre_estudiante2) VALUES
(1, 'Laura Medina', 'Camilo Ortega'),
(3, 'Valentina Rojas', 'Santiago Mora'),
(4, 'Daniela Salazar', 'Juan Pablo Leon');

INSERT IGNORE INTO formatos_ppa (id_form_a0, nombre_asesor, nombre_estudiante1, ruta_carta_aceptacion) VALUES
(2, 'Ing. Martha Riascos', 'Miguel Cabrera', '/cartas/ppa/formato2.pdf'),
(5, 'Ing. Hector Narvaez', 'Natalia Cardenas', '/cartas/ppa/formato5.pdf'),
(6, 'Ing. Juliana Rengifo', 'Andres Quiroga', '/cartas/ppa/formato6.pdf');

INSERT IGNORE INTO estados (id_estado, estado_actual, fecha_registro_estado, id_form_a0) VALUES
(1, 'En formulacion', '2026-02-15', 1),
(2, 'Por corregir', '2026-02-20', 2),
(3, 'En evaluacion', '2026-03-01', 3),
(4, 'En formulacion', '2026-03-02', 4),
(5, 'Aprobado', '2026-03-10', 5),
(6, 'Por corregir', '2026-03-12', 6);

INSERT IGNORE INTO evaluaciones (id_evaluacion, concepto, fecha_registro_concepto, nombre_coordinador, id_form_a0) VALUES
(1, 'Por corregir', '2026-02-25', 'Martha Riascos', 1),
(2, 'Sin concepto aun por establecer', '2026-03-01', 'Coordinacion FIET', 1),
(3, 'Por corregir', '2026-02-28', 'Hector Narvaez', 2),
(4, 'Aprobado', '2026-03-15', 'Juliana Rengifo', 3),
(5, 'Por corregir', '2026-03-18', 'Martha Riascos', 4),
(6, 'Aprobado', '2026-03-20', 'Coordinacion FIET', 5),
(7, 'Por corregir', '2026-03-22', 'Hector Narvaez', 6);

INSERT IGNORE INTO observaciones (id_observacion, observacion, fecha_registro_observacion, id_evaluacion) VALUES
(1, 'Ajustar marco teorico y ampliar antecedentes internacionales.', '2026-02-26', 1),
(2, 'Replantear alcance de la implementacion para el primer corte.', '2026-02-27', 1),
(3, 'Definir con mayor precision indicadores de validacion.', '2026-03-03', 3),
(4, 'Incluir cronograma detallado con hitos mensuales.', '2026-03-05', 3),
(5, 'Fortalecer metodologia de pruebas con casos borde.', '2026-03-16', 4),
(6, 'Ajustar redaccion del objetivo general para mayor claridad.', '2026-03-19', 5),
(7, 'Agregar analisis de riesgos tecnicos y plan de mitigacion.', '2026-03-21', 7),
(8, 'Incluir referencias bibliograficas en formato IEEE.', '2026-03-23', 7);

INSERT IGNORE INTO observacion_docentes (id_observacion, id_docente) VALUES
(1, 2),
(1, 4),
(2, 3),
(3, 1),
(3, 2),
(4, 4),
(5, 6),
(6, 2),
(7, 1),
(7, 3),
(8, 4),
(8, 6);
