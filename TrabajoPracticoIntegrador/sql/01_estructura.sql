-- 01_estructura.sql
-- Script de creacion de la base de datos y tablas para el TFI

CREATE DATABASE tpibasedatos;
USE tpibasedatos;

CREATE TABLE microchip (
  id                 BIGINT AUTO_INCREMENT PRIMARY KEY,                      
  eliminado          BOOLEAN NOT NULL DEFAULT FALSE,
  codigo             VARCHAR(25) NOT NULL UNIQUE,               
  fecha_implantacion DATE,
  veterinaria        VARCHAR(120),
  observaciones      VARCHAR(255),

  CONSTRAINT ck_len_codigo        CHECK (CHAR_LENGTH(codigo) <= 25),
  CONSTRAINT ck_len_veterinaria   CHECK (veterinaria IS NULL OR CHAR_LENGTH(veterinaria)  <= 120),
  CONSTRAINT ck_len_observaciones CHECK (observaciones IS NULL OR CHAR_LENGTH(observaciones) <= 255),
  CONSTRAINT ck_implantacion_vet  CHECK (fecha_implantacion IS NULL OR veterinaria IS NOT NULL)
);

CREATE TABLE mascota (
  id               BIGINT AUTO_INCREMENT PRIMARY KEY,                          
  eliminado        BOOLEAN NOT NULL DEFAULT FALSE,
  nombre           VARCHAR(60)  NOT NULL,
  especie          VARCHAR(30)  NOT NULL,
  raza             VARCHAR(60),
  fecha_nacimiento DATE,
  duenio           VARCHAR(120) NOT NULL,

 
  microchip_id     BIGINT UNIQUE, 
  CONSTRAINT fk_mascota_microchip
    FOREIGN KEY (microchip_id)
    REFERENCES microchip(id)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT,

  CONSTRAINT ck_len_nombre   CHECK (CHAR_LENGTH(nombre)  <= 60),
  CONSTRAINT ck_len_especie  CHECK (CHAR_LENGTH(especie) <= 30),
  CONSTRAINT ck_len_raza     CHECK (raza   IS NULL OR CHAR_LENGTH(raza)   <= 60),
  CONSTRAINT ck_len_duenio   CHECK (CHAR_LENGTH(duenio)  <= 120)
);
