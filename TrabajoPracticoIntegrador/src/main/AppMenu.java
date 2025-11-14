/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;


import entities.Mascota;
import service.MascotaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final MascotaService mascotaService;
    private final Scanner scanner;

    public AppMenu() {
        this.mascotaService = new MascotaService();
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
        System.out.println("0 - Salir");
    }

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

    // ===== Opciones del menu =====

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
                LocalDate fecha = LocalDate.parse(fechaStr);
                mascota.setFechaNacimiento(fecha);
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
}
