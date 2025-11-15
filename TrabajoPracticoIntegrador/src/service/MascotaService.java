/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import config.DatabaseConnection;
import dao.MascotaDao;
import dao.MicrochipDao;
import entities.Mascota;
import entities.Microchip;

import java.sql.Connection;
import java.util.List;

public class MascotaService implements GenericService<Mascota> {

    private final MascotaDao mascotaDao;
    private final MicrochipDao microchipDao;

    public MascotaService() {
        this.mascotaDao = new MascotaDao();
        this.microchipDao = new MicrochipDao();
    }

    // ==== CRUD normal de mascota ====

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

    // ==== Transaccion: crear mascota + microchip juntos ====

    public void crearMascotaConMicrochip(Mascota mascota, Microchip microchip) throws Exception {

        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser null");
        }
        if (microchip == null) {
            throw new IllegalArgumentException("El microchip no puede ser null");
        }
        if (microchip.getCodigo() == null || microchip.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El codigo de microchip no puede estar vacio");
        }

        // mismas validaciones basicas de antes
        if (mascota.getNombre() == null || mascota.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacio");
        }
        if (mascota.getEspecie() == null || mascota.getEspecie().isBlank()) {
            throw new IllegalArgumentException("La especie no puede estar vacia");
        }
        if (mascota.getDuenio() == null || mascota.getDuenio().isBlank()) {
            throw new IllegalArgumentException("El duenio no puede estar vacio");
        }

        mascota.setEliminado(false);
        microchip.setEliminado(false);

        Connection con = null;

        try {
            // 1) abrir conexion y desactivar autoCommit
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            // 2) crear microchip en la BD usando la misma conexion
            microchipDao.crear(microchip, con);

            // 3) asociar el microchip a la mascota
            mascota.setMicrochip(microchip);

            // 4) crear la mascota en la BD usando la misma conexion
            mascotaDao.crear(mascota, con);

            // 5) si todo salio bien, confirmar la transaccion
            con.commit();
            System.out.println("Transaccion OK: se crearon mascota y microchip");

        } catch (Exception e) {
            // si algo falla, revertimos todo
            if (con != null) {
                try {
                    con.rollback();
                    System.out.println("Transaccion revertida (rollback)");
                } catch (Exception ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw e;

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (Exception ex) {
                    System.out.println("Error al cerrar la conexion: " + ex.getMessage());
                }
            }
        }
    }

    // ==== Metodos requeridos por GenericService ====

    @Override
    public void insertar(Mascota entidad) throws Exception {
        // Reusamos la logica existente
        crearMascota(entidad);
    }

    @Override
    public Mascota getById(Long id) throws Exception {
        return buscarPorId(id);
    }

    @Override
    public List<Mascota> getAll() throws Exception {
        return listarMascotas();
    }

    @Override
    public void actualizar(Mascota entidad) throws Exception {
        actualizarMascota(entidad);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        eliminarMascota(id);
    }
}
