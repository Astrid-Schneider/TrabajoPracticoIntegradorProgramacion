/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.MicrochipDao;
import entities.Microchip;

import java.util.List;

public class MicrochipService {

    private MicrochipDao microchipDao;

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
}
