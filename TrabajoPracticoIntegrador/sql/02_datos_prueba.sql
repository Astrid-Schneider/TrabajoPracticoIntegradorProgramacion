-- Archivo: datos_prueba.sql
-- Datos de prueba para TPIBaseDatos

USE tpibasedatos;

-- =========================
-- 1) Datos de microchip
-- =========================

INSERT INTO microchip (eliminado, codigo, fecha_implantacion, veterinaria, observaciones) VALUES
  (0, 'CHIP-100', '2024-01-10', 'Vet Centro', 'Control general'),
  (0, 'CHIP-200', NULL,          NULL,        'Rescatado sin datos de veterinaria'),
  (0, 'CHIP-300', '2023-12-05', 'Clinica Norte', 'Vacunas al dia'),
  (0, 'CHIP-400', NULL,          NULL,        'Chip encontrado sin informacion'),
  (1, 'CHIP-500', '2022-06-01', 'Vet Sur', 'Chip dado de baja');

-- =========================
-- 2) Datos de mascota
-- =========================

INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio, microchip_id) VALUES
  (0, 'Luna',  'Perro', 'Caniche',  '2021-08-20', 'Astrid',      1),
  (0, 'Rocky', 'Perro', 'Labrador', '2020-05-10', 'Astrid',      3),
  (0, 'Mia',   'Gato',  'Siames',   '2022-03-01', 'Astrid',      NULL),
  (0, 'Toby',  'Perro', 'Mestizo',        NULL,   'Juan Perez',  2),
  (0, 'Lola',  'Gato',  'Naranja',  '2019-11-15', 'Maria Lopez', NULL),
  (1, 'Max',   'Perro', 'Ovejero',  '2018-07-07', 'Ana Ruiz',    4);
