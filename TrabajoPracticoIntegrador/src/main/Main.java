/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template*/


package main;

import dao.MascotaDao;
import entities.Mascota;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            MascotaDao mascotaDao = new MascotaDao();

            // 1) Creamos una mascota nueva y la guardamos
            Mascota mascota = new Mascota();
            mascota.setEliminado(false);
            mascota.setNombre("Luna");
            mascota.setEspecie("Perro");
            mascota.setRaza("Caniche");
            mascota.setFechaNacimiento(LocalDate.of(2021, 8, 20));
            mascota.setDuenio("Astrid");
            mascota.setMicrochip(null);

            mascotaDao.crear(mascota);
            System.out.println("Mascota creada con id: " + mascota.getId());

            // 2) Eliminamos (baja logica)
            mascotaDao.eliminar(mascota.getId());
            System.out.println("Mascota marcada como eliminada en la BD");

            // 3) Listamos todas las mascotas no eliminadas
            System.out.println("Listado de mascotas no eliminadas:");
            List<Mascota> mascotas = mascotaDao.leerTodos();
            for (Mascota m : mascotas) {
                System.out.println(m);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}