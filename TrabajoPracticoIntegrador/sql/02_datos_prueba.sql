-- 02_datos_prueba.sql
-- Script con datos de prueba para el TFI

USE tpibasedatos;

-- microchips de ejemplo
INSERT INTO microchip (eliminado, codigo, fecha_implantacion, veterinaria, observaciones)
VALUES
    (0, 'CHIP-001', '2024-05-10', 'Veterinaria Central', 'Sin observaciones'),
    (0, 'CHIP-002', '2023-11-03', 'Veterinaria Norte', 'Control anual'),
    (0, 'CHIP-003', NULL, NULL, 'Aun no implantado');

-- mascotas de ejemplo
INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio, microchip_id)
VALUES
    (0, 'Luna',  'Perro', 'Caniche',  '2021-08-20', 'Astrid', NULL),
    (0, 'Rocky', 'Perro', 'Labrador', '2020-05-10', 'Astrid', 1), -- usa CHIP-001
    (0, 'Mia',   'Gato',  'Siames',   '2022-03-01', 'Astrid', 2); -- usa CHIP-002
