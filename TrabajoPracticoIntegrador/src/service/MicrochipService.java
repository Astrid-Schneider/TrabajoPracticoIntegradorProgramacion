/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.MicrochipDao;
import entities.Microchip;

import java.util.List;

public class MicrochipService implements GenericService<Microchip> {

    private final MicrochipDao microchipDao;;

    public MicrochipService() {
        this.microchipDao = new MicrochipDao();
    }

    public void crearMicrochip(Microchip microchip) throws Exception {
        if (microchip.getCodigo() == null || microchip.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio");
        }

        microchip.setEliminado(false);
        microchipDao.crear(microchip);
    }

    public Microchip buscarPorId(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id invalido");
        }
        return microchipDao.leer(id);
    }

    public List<Microchip> listarTodos() throws Exception {
        return microchipDao.leerTodos();
    }

    public void actualizarMicrochip(Microchip microchip) throws Exception {
        if (microchip.getId() == null || microchip.getId() <= 0) {
            throw new IllegalArgumentException("Id invalido");
        }
        if (microchip.getCodigo() == null || microchip.getCodigo().isBlank()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio");
        }

        microchipDao.actualizar(microchip);
    }

    public void eliminarMicrochip(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id invalido");
        }
        microchipDao.eliminar(id);
    }
    
        // ==== Metodos requeridos por GenericService ====

    @Override
    public void insertar(Microchip entidad) throws Exception {
        crearMicrochip(entidad);
    }

    @Override
    public Microchip getById(Long id) throws Exception {
        return buscarPorId(id);
    }

    @Override
    public List<Microchip> getAll() throws Exception {
        return listarTodos();
    }

    @Override
    public void actualizar(Microchip entidad) throws Exception {
        actualizarMicrochip(entidad);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        eliminarMicrochip(id);
    }
}
