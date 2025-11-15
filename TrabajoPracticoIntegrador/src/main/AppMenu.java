/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entities.Mascota;
import entities.Microchip;
import service.MascotaService;
import service.MicrochipService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final MascotaService mascotaService;
    private final MicrochipService microchipService;
    private final Scanner scanner;

    public AppMenu() {
        this.mascotaService = new MascotaService();
        this.microchipService = new MicrochipService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Ingrese una opcion: ");

            switch (opcion) {
                case 1 -> crearMascota();
                case 2 -> listarMascotas();
                case 3 -> buscarMascotaPorId();
                case 4 -> actualizarMascota();
                case 5 -> eliminarMascota();
                case 6 -> buscarMascotasPorDuenio();
                case 7 -> crearMicrochip();
                case 8 -> listarMicrochips();
                case 9 -> buscarMicrochipPorId();
                case 10 -> actualizarMicrochip();
                case 11 -> eliminarMicrochip();
                case 12 -> crearMascotaConMicrochipTransaccion();
                case 0 -> System.out.println("Saliendo de la aplicacion...");
                default -> System.out.println("Opcion invalida");
            }

            System.out.println();

        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("==== MENU MASCOTAS ====");
        System.out.println("1 - Crear mascota");
        System.out.println("2 - Listar mascotas");
        System.out.println("3 - Buscar mascota por id");
        System.out.println("4 - Actualizar mascota");
        System.out.println("5 - Eliminar mascota (baja logica)");
        System.out.println("6 - Buscar mascotas por duenio");
        System.out.println("7 - Crear microchip");
        System.out.println("8 - Listar microchips");
        System.out.println("9 - Buscar microchip por id");
        System.out.println("10 - Actualizar microchip");
        System.out.println("11 - Eliminar microchip (baja logica)");
        System.out.println("12 - Crear mascota y microchip (transaccion)");
        System.out.println("0 - Salir");
    }

    // ========= Helpers de lectura =========

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            scanner.next(); // descarta lo que escribio mal
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpia el enter
        return valor;
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private Long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                linea = linea.trim();
                return Long.parseLong(linea);
            } catch (NumberFormatException e) {
                System.out.println("Valor no valido. Ingrese un numero entero.");
            }
        }
    }

    // ========= Opciones de mascota =========

    private void crearMascota() {
        try {
            System.out.println("=== Crear nueva mascota ===");
            String nombre = leerTexto("Nombre: ");
            String especie = leerTexto("Especie: ");
            String raza = leerTexto("Raza: ");
            String duenio = leerTexto("Duenio: ");
            String fechaStr = leerTexto("Fecha nacimiento (yyyy-mm-dd) o enter si no sabe: ");

            Mascota mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setEspecie(especie);
            mascota.setRaza(raza);
            mascota.setDuenio(duenio);

            if (!fechaStr.isBlank()) {
                try {
                    LocalDate fecha;

                    if (fechaStr.contains("/")) {
                        // formato dd/MM/yyyy, ejemplo 20/11/2020
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaStr, formatter);
                    } else {
                        // formato por defecto yyyy-MM-dd, ejemplo 2020-11-20
                        fecha = LocalDate.parse(fechaStr);
                    }

                    mascota.setFechaNacimiento(fecha);

                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha invalido. Use 2020-11-20 o 20/11/2020. No se guardara la fecha.");
                }
            }

            mascotaService.crearMascota(mascota);

            System.out.println("Mascota creada con id: " + mascota.getId());

        } catch (Exception e) {
            System.out.println("Error al crear mascota: " + e.getMessage());
        }
    }

    private void listarMascotas() {
        try {
            System.out.println("=== Listado de mascotas ===");
            List<Mascota> mascotas = mascotaService.listarMascotas();
            for (Mascota m : mascotas) {
                System.out.println(m);
            }
        } catch (Exception e) {
            System.out.println("Error al listar mascotas: " + e.getMessage());
        }
    }

    private void buscarMascotaPorId() {
        try {
            System.out.println("=== Buscar mascota por id ===");
            long id = leerEntero("Ingrese id: ");
            Mascota mascota = mascotaService.buscarPorId(id);

            if (mascota != null) {
                System.out.println("Mascota encontrada:");
                System.out.println(mascota);
            } else {
                System.out.println("No se encontro mascota con ese id");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar mascota: " + e.getMessage());
        }
    }

    private void buscarMascotasPorDuenio() {
        try {
            System.out.println("=== Buscar mascotas por duenio ===");
            String duenio = leerTexto("Ingrese nombre del duenio (o parte del nombre): ");

            List<Mascota> mascotas = mascotaService.buscarPorDuenio(duenio);

            if (mascotas.isEmpty()) {
                System.out.println("No se encontraron mascotas para ese duenio");
            } else {
                System.out.println("Mascotas encontradas:");
                for (Mascota m : mascotas) {
                    System.out.println(m);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar por duenio: " + e.getMessage());
        }
    }

    private void actualizarMascota() {
        try {
            System.out.println("=== Actualizar mascota ===");
            long id = leerEntero("Ingrese id de la mascota a actualizar: ");
            Mascota mascota = mascotaService.buscarPorId(id);

            if (mascota == null) {
                System.out.println("No existe mascota con ese id");
                return;
            }

            System.out.println("Mascota actual:");
            System.out.println(mascota);

            String nuevoNombre = leerTexto("Nuevo nombre (enter para dejar igual): ");
            String nuevaRaza = leerTexto("Nueva raza (enter para dejar igual): ");
            String nuevoDuenio = leerTexto("Nuevo duenio (enter para dejar igual): ");

            if (!nuevoNombre.isBlank()) {
                mascota.setNombre(nuevoNombre);
            }
            if (!nuevaRaza.isBlank()) {
                mascota.setRaza(nuevaRaza);
            }
            if (!nuevoDuenio.isBlank()) {
                mascota.setDuenio(nuevoDuenio);
            }

            mascotaService.actualizarMascota(mascota);
            System.out.println("Mascota actualizada");

        } catch (Exception e) {
            System.out.println("Error al actualizar mascota: " + e.getMessage());
        }
    }

    private void eliminarMascota() {
        try {
            System.out.println("=== Eliminar mascota (baja logica) ===");
            long id = leerEntero("Ingrese id de la mascota a eliminar: ");
            mascotaService.eliminarMascota(id);
            System.out.println("Mascota marcada como eliminada");
        } catch (Exception e) {
            System.out.println("Error al eliminar mascota: " + e.getMessage());
        }
    }

    // ========= Opciones de microchip =========

    private void crearMicrochip() {
        try {
            System.out.println("=== Crear nuevo microchip ===");
            String codigo = leerTexto("Codigo: ");
            String veterinaria = leerTexto("Veterinaria (enter si no sabe): ");
            String observaciones = leerTexto("Observaciones (enter si no hay): ");
            String fechaStr = leerTexto("Fecha implantacion (yyyy-mm-dd o dd/mm/yyyy, enter si no tiene): ");

            Microchip micro = new Microchip();
            micro.setCodigo(codigo);
            micro.setVeterinaria(veterinaria.isBlank() ? null : veterinaria);
            micro.setObservaciones(observaciones.isBlank() ? null : observaciones);

            if (!fechaStr.isBlank()) {
                try {
                    LocalDate fecha;
                    if (fechaStr.contains("/")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaStr, formatter);
                    } else {
                        fecha = LocalDate.parse(fechaStr);
                    }
                    micro.setFechaImplantacion(fecha);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha invalido. No se guarda la fecha.");
                }
            }

            microchipService.crearMicrochip(micro);
            System.out.println("Microchip creado con id: " + micro.getId());

        } catch (Exception e) {
            System.out.println("Error al crear microchip: " + e.getMessage());
        }
    }

    private void listarMicrochips() {
    try {
        System.out.println("=== Listado de microchips ===");
        // ACA EL CAMBIO:
        List<Microchip> chips = microchipService.listarTodos();

        if (chips.isEmpty()) {
            System.out.println("No hay microchips para mostrar");
        } else {
            for (Microchip m : chips) {
                System.out.println(m);
            }
        }
    } catch (Exception e) {
        System.out.println("Error al listar microchips: " + e.getMessage());
    }
}

    private void buscarMicrochipPorId() {
        try {
            System.out.println("=== Buscar microchip por id ===");
            Long id = leerLong("Ingrese id: ");
            Microchip micro = microchipService.buscarPorId(id);
            if (micro == null) {
                System.out.println("No se encontro microchip con ese id");
            } else {
                System.out.println("Microchip encontrado:");
                System.out.println(micro);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar microchip: " + e.getMessage());
        }
    }

    private void actualizarMicrochip() {
        try {
            System.out.println("=== Actualizar microchip ===");
            Long id = leerLong("Ingrese id del microchip a actualizar: ");
            Microchip micro = microchipService.buscarPorId(id);

            if (micro == null) {
                System.out.println("No se encontro microchip con ese id");
                return;
            }

            System.out.println("Microchip actual:");
            System.out.println(micro);

            String nuevoCodigo = leerTexto("Nuevo codigo (enter para dejar igual): ");
            String nuevaVet = leerTexto("Nueva veterinaria (enter para dejar igual): ");
            String nuevasObs = leerTexto("Nuevas observaciones (enter para dejar igual): ");
            String fechaStr = leerTexto("Nueva fecha implantacion (yyyy-mm-dd o dd/mm/yyyy, enter para dejar igual / null): ");

            if (!nuevoCodigo.isBlank()) {
                micro.setCodigo(nuevoCodigo);
            }
            if (!nuevaVet.isBlank()) {
                micro.setVeterinaria(nuevaVet);
            }
            if (!nuevasObs.isBlank()) {
                micro.setObservaciones(nuevasObs);
            }

            if (!fechaStr.isBlank()) {
                try {
                    LocalDate fecha;
                    if (fechaStr.contains("/")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaStr, formatter);
                    } else {
                        fecha = LocalDate.parse(fechaStr);
                    }
                    micro.setFechaImplantacion(fecha);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha invalido. No se cambia la fecha.");
                }
            }

            microchipService.actualizarMicrochip(micro);
            System.out.println("Microchip actualizado");

        } catch (Exception e) {
            System.out.println("Error al actualizar microchip: " + e.getMessage());
        }
    }

    private void eliminarMicrochip() {
        try {
            System.out.println("=== Eliminar microchip (baja logica) ===");
            Long id = leerLong("Ingrese id del microchip a eliminar: ");
            microchipService.eliminarMicrochip(id);
            System.out.println("Microchip marcado como eliminado");
        } catch (Exception e) {
            System.out.println("Error al eliminar microchip: " + e.getMessage());
        }
    }

    // ========= Opcion 12: transaccion mascota + microchip =========

    private void crearMascotaConMicrochipTransaccion() {
        try {
            System.out.println("=== Crear mascota y microchip (transaccion) ===");

            // Datos de la mascota
            String nombre = leerTexto("Nombre mascota: ");
            String especie = leerTexto("Especie: ");
            String raza = leerTexto("Raza: ");
            String duenio = leerTexto("Duenio: ");
            String fechaMascotaStr = leerTexto("Fecha nacimiento (yyyy-mm-dd o dd/mm/yyyy, enter si no sabe): ");

            Mascota mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setEspecie(especie);
            mascota.setRaza(raza);
            mascota.setDuenio(duenio);

            if (!fechaMascotaStr.isBlank()) {
                try {
                    LocalDate fecha;
                    if (fechaMascotaStr.contains("/")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaMascotaStr, formatter);
                    } else {
                        fecha = LocalDate.parse(fechaMascotaStr);
                    }
                    mascota.setFechaNacimiento(fecha);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha invalido para mascota. No se guarda la fecha.");
                }
            }

            // Datos del microchip
            String codigoChip = leerTexto("Codigo microchip: ");
            String vet = leerTexto("Veterinaria (enter si no sabe): ");
            String obs = leerTexto("Observaciones (enter si no hay): ");
            String fechaChipStr = leerTexto("Fecha implantacion (yyyy-mm-dd o dd/mm/yyyy, enter si no tiene): ");

            Microchip micro = new Microchip();
            micro.setCodigo(codigoChip);
            micro.setVeterinaria(vet.isBlank() ? null : vet);
            micro.setObservaciones(obs.isBlank() ? null : obs);

            if (!fechaChipStr.isBlank()) {
                try {
                    LocalDate fecha;
                    if (fechaChipStr.contains("/")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaChipStr, formatter);
                    } else {
                        fecha = LocalDate.parse(fechaChipStr);
                    }
                    micro.setFechaImplantacion(fecha);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha invalido para microchip. No se guarda la fecha.");
                }
            }

            // Llamamos al servicio que maneja la transaccion
            mascotaService.crearMascotaConMicrochip(mascota, micro);

            System.out.println("Mascota creada con id: " + mascota.getId());
            System.out.println("Microchip creado con id: " + micro.getId());

        } catch (Exception e) {
            System.out.println("Error en transaccion mascota + microchip: " + e.getMessage());
        }
    }
}

