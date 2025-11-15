/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.MascotaDao;
import entities.Mascota;

import java.util.List;

public class MascotaService {

    private final MascotaDao mascotaDao;

    public MascotaService() {
        this.mascotaDao = new MascotaDao();
    }

    public void crearMascota(Mascota mascota) throws Exception {
        // Validaciones muy basicas de ejemplo
        if (mascota.getNombre() == null || mascota.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacio");
        }
        if (mascota.getEspecie() == null || mascota.getEspecie().isBlank()) {
            throw new IllegalArgumentException("La especie no puede estar vacia");
        }
        if (mascota.getDuenio() == null || mascota.getDuenio().isBlank()) {
            throw new IllegalArgumentException("El duenio no puede estar vacio");
        }

        mascota.setEliminado(false); // siempre se crea como no eliminada

        mascotaDao.crear(mascota);
    }
    
    public List<Mascota> buscarPorDuenio(String duenio) throws Exception {
        if (duenio == null || duenio.isBlank()) {
        throw new IllegalArgumentException("El duenio no puede estar vacio");
    }

    return mascotaDao.buscarPorDuenio(duenio);
}

    public Mascota buscarPorId(Long id) throws Exception {
        return mascotaDao.leer(id);
    }

    public List<Mascota> listarMascotas() throws Exception {
        return mascotaDao.leerTodos();
    }

    public void actualizarMascota(Mascota mascota) throws Exception {
        mascotaDao.actualizar(mascota);
    }

    public void eliminarMascota(Long id) throws Exception {
        mascotaDao.eliminar(id);
    }
}
