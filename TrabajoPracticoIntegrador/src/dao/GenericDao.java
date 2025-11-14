/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;

public interface GenericDao<T> {

    // Crear un nuevo registro en la base de datos
    void crear(T entidad) throws Exception;

    // Buscar por id (puede devolver null si no existe)
    T leer(Long id) throws Exception;

    // Listar todos los registros (que no estén eliminados, en general)
    List<T> leerTodos() throws Exception;

    // Actualizar un registro existente
    void actualizar(T entidad) throws Exception;

    // Baja lógica: marcar eliminado = true
    void eliminar(Long id) throws Exception;
}