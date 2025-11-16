Trabajo Práctico Integrador – Programación 2
Sistema de Gestión de Mascotas y Microchips
Tecnicatura Universitaria en Programación
Universidad Tecnológica Nacional

Este Trabajo Práctico Integrador implementa un sistema en Java que gestiona Mascotas y sus Microchips, trabajando con Programación Orientada a Objetos, JDBC y Arquitectura en Capas.
Se desarrolla una relación 1 → 1 unidireccional, donde Mascota conoce a Microchip, pero no al revés.
El sistema permite realizar operaciones CRUD completas y una transacción con commit/rollback, cumpliendo con todos los puntos de la rúbrica.

Equipo
Astrid Schneider
(Acá agregan los demás integrantes)
Comisión 7 – Tecnicatura Universitaria en Programación – UTN

Objetivos Académicos
Este proyecto integra los siguientes conceptos:
________________________________________
1. Arquitectura en Capas (Layered Architecture)
Separación clara de responsabilidades:
Presentación (main/ui)
Menú por consola
Lectura validada de datos
Lógica de Negocio (Service)
 Validaciones
 Lógica de negocio
 Transacción Mascota + Microchip (commit/rollback)
Acceso a Datos (DAO + JDBC)
 PreparedStatement (evita SQL Injection)
 CRUD completo
 DAO genérico y DAO concretos
Modelo (Entities)
 Clases Mascota y Microchip
 Encapsulamiento
 Representación del dominio real
________________________________________
2. Programación Orientada a Objetos
•	Uso de interfaces genéricas (GenericDAO, GenericService)
•	Encapsulamiento con getters/setters
•	toString() sobrescrito en todas las entidades
•	Validaciones en Service
•	Relación 1 → 1 entre entidades
•	Separación de responsabilidades (Single Responsibility Principle)
________________________________________
3. Persistencia con JDBC y MySQL
•	Conexión mediante clase DatabaseConnection
•	Manejo manual de conexiones y cierre seguro
•	PreparedStatement parametrizados
•	Manejo completo de ResultSet
•	FOREIGN KEY con UNIQUE para la relación 1→1
•	Scripts SQL incluidos en carpeta /sql
________________________________________
4. Manejo de Excepciones
•	try–catch controlados
•	retry en lecturas numéricas
•	rollback en transacciones compuestas
•	Validaciones cuando faltan campos obligatorios
•	Manejo de excepciones en cierre de conexión
________________________________________
5. Patrones de Diseño Utilizados
•	DAO Pattern → acceso a BD
•	Service Layer Pattern → lógica de negocio
•	Factory Pattern → creación de conexiones
•	Soft Delete Pattern → eliminación lógica
•	Generic Pattern → DAO y Service reutilizables

 
Funcionalidades Implementadas

Mascotas
 Crear mascota
 Listar todas
 Buscar por ID
 Buscar por dueño (LIKE)
 Actualizar
 Eliminar (baja lógica)

Microchips
 Crear microchip
 Listar todos
 Buscar por ID
 Actualizar
 Eliminar (baja lógica)

Transacción con commit/rollback
 Crear Mascota + Microchip juntos
 Si algo falla → rollback automático
 Todo dentro de la misma conexión

Requisitos para compilar y ejecutar

 Java 21+
 NetBeans 21 o superior
 MySQL 8+
 Driver JDBC ya incluido vía librería (mysql-connector-j)
(si falta, agregarlo desde NetBeans → Libraries → Add JAR)

Instalación

Crear Base de Datos
Ejecutar el archivo:
/sql/01_estructura.sql
Incluye:
•	CREATE DATABASE
•	CREATE TABLE microchip
•	CREATE TABLE mascota
•	FOREIGN KEY + UNIQUE
•	CHECKS obligatorios
•	Restricciones del TFI
Luego ejecutar:
/sql/02_datos_prueba.sql
Incluye:
•	Inserción de microchips de prueba
•	Inserción de mascotas asociadas
________________________________________

Configurar Conexión

En config/DatabaseConnection.java:
private static final String URL = "jdbc:mysql://localhost:3306/tpibasedatos";
private static final String USER = "root";
private static final String PASSWORD = "";
Modificar si tu MySQL usa otra contraseña.
________________________________________

Ejecutar el Proyecto

Desde NetBeans:
•	Abrir proyecto
•	Ejecutar Main.java o AppMenu
Aparecerá el menú:
==== MENU MASCOTAS ====
1 - Crear mascota
2 - Listar mascotas
3 - Buscar mascota por id
4 - Actualizar mascota
5 - Eliminar mascota (baja logica)
6 - Buscar mascotas por duenio
7 - Crear microchip
8 - Listar microchips
9 - Buscar microchip por id
10 - Actualizar microchip
11 - Eliminar microchip
12 - Crear mascota y microchip (transaccion)
0 - Salir
________________________________________

Ejemplo de Uso

Crear Mascota
(pegar algo)
Crear Microchip
(pegar algo)
Transacción (opción 12)
Crea microchip + mascota juntos.
Si la creación del microchip falla → rollback.

Modelo de Datos (aca va el UML) 
   microchip (1)  ←── UNIQUE FK ── (1) mascota
Tabla microchip
•	id (PK)
•	eliminado
•	código (UNIQUE)
•	fecha_implantacion
•	veterinaria
•	observaciones
Tabla mascota
•	id (PK)
•	eliminado
•	nombre
•	especie
•	raza
•	fecha_nacimiento
•	duenio
•	microchip_id (FK UNIQUE → microchip.id)
Relación unidireccional: Mascota conoce a Microchip.

 Reglas de Negocio

 El dueño no puede ser vacío
 Nombre y especie son obligatorios
 Microchip debe existir antes de asociarse
 No se listan registros eliminados
 No se puede actualizar una mascota sin ID
 Transacción completa Mascota–Microchip con rollback

Video demostración

(Agregar link YouTube o Drive cuando lo graben. Obligatorio según la rúbrica.)

