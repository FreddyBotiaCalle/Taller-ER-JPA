-- V2__estado_mapsid.sql
-- Alinea la tabla `estados` con la semántica de @MapsId en JPA:
-- - asegura que `id_estado` coincide con `id_form_a0`
-- - añade constraint FK hacia `formatosa(id_form_a0)` con ON DELETE CASCADE
-- - añade índice único sobre `id_form_a0`

START TRANSACTION;

-- Asegurar valores consistentes: hacer que id_estado copie id_form_a0
UPDATE estados SET id_estado = id_form_a0;

-- Garantizar que la clave primaria existe sobre id_estado
ALTER TABLE estados DROP PRIMARY KEY;
ALTER TABLE estados ADD PRIMARY KEY (id_estado);

-- Añadir índice único sobre id_form_a0 para reforzar la relación 1:1
ALTER TABLE estados ADD UNIQUE INDEX ux_estados_id_form_a0 (id_form_a0);

-- Añadir FK hacia formatosa(id_form_a0) con borrado en cascada
ALTER TABLE estados
  ADD CONSTRAINT fk_estados_formatosa FOREIGN KEY (id_form_a0)
  REFERENCES formatosa(id_form_a0) ON DELETE CASCADE ON UPDATE CASCADE;

COMMIT;
