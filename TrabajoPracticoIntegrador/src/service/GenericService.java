/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;

public interface GenericService<T> {

    void insertar(T entidad) throws Exception;

    T getById(Long id) throws Exception;

    List<T> getAll() throws Exception;

    void actualizar(T entidad) throws Exception;

    void eliminar(Long id) throws Exception;
}
