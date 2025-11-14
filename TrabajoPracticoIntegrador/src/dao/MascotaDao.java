/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import entities.Mascota;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;

    public class MascotaDao implements GenericDao<Mascota> {
        
    private static final String INSERT_SQL =
            "INSERT INTO mascota (eliminado, nombre, especie, raza, fecha_nacimiento, duenio, microchip_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
        

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, eliminado, nombre, especie, raza, fecha_nacimiento, duenio, microchip_id " +
            "FROM mascota " +
            "WHERE id = ?";
    
        private static final String SELECT_ALL_SQL =
            "SELECT id, eliminado, nombre, especie, raza, fecha_nacimiento, duenio, microchip_id " +
            "FROM mascota " +
            "WHERE eliminado = 0";
        
            private static final String UPDATE_SQL =
            "UPDATE mascota " +
            "SET eliminado = ?, nombre = ?, especie = ?, raza = ?, fecha_nacimiento = ?, duenio = ?, microchip_id = ? " +
            "WHERE id = ?";
            
        private static final String DELETE_LOGICO_SQL =
        "UPDATE mascota " +
        "SET eliminado = 1 " +
        "WHERE id = ?";



@Override
public void crear(Mascota entidad) throws Exception {

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

        boolean eliminado = entidad.getEliminado() != null ? entidad.getEliminado() : false;
        ps.setBoolean(1, eliminado);

        ps.setString(2, entidad.getNombre());
        ps.setString(3, entidad.getEspecie());
        ps.setString(4, entidad.getRaza());

        if (entidad.getFechaNacimiento() != null) {
            ps.setDate(5, Date.valueOf(entidad.getFechaNacimiento()));
        } else {
            ps.setNull(5, Types.DATE);
        }

        ps.setString(6, entidad.getDuenio());

        if (entidad.getMicrochip() != null && entidad.getMicrochip().getId() != null) {
            ps.setLong(7, entidad.getMicrochip().getId());
        } else {
            ps.setNull(7, Types.BIGINT);
        }

        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                long idGenerado = rs.getLong(1);
                entidad.setId(idGenerado);
            }
        }
    }
}

    @Override
    public Mascota leer(Long id) throws Exception {

        Mascota mascota = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            // Cargamos el parámetro ? con el id
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    mascota = new Mascota();

                    // Campos básicos
                    mascota.setId(rs.getLong("id"));
                    mascota.setEliminado(rs.getBoolean("eliminado"));
                    mascota.setNombre(rs.getString("nombre"));
                    mascota.setEspecie(rs.getString("especie"));
                    mascota.setRaza(rs.getString("raza"));

                    // fecha_nacimiento puede ser null
                    java.sql.Date fechaSql = rs.getDate("fecha_nacimiento");
                    if (fechaSql != null) {
                        mascota.setFechaNacimiento(fechaSql.toLocalDate());
                    }

                    mascota.setDuenio(rs.getString("duenio"));

                    // microchip_id puede ser null
                    long microchipId = rs.getLong("microchip_id");
                    if (!rs.wasNull()) {
                        entities.Microchip microchip = new entities.Microchip();
                        microchip.setId(microchipId);
                        mascota.setMicrochip(microchip);
                    } else {
                        mascota.setMicrochip(null);
                    }
                }
            }
        }

        // Si no encontró nada, devuelve null
        return mascota;
    }


    @Override
    public List<Mascota> leerTodos() throws Exception {

        List<Mascota> mascotas = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mascota mascota = new Mascota();

                mascota.setId(rs.getLong("id"));
                mascota.setEliminado(rs.getBoolean("eliminado"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setRaza(rs.getString("raza"));

                java.sql.Date fechaSql = rs.getDate("fecha_nacimiento");
                if (fechaSql != null) {
                    mascota.setFechaNacimiento(fechaSql.toLocalDate());
                }

                mascota.setDuenio(rs.getString("duenio"));

                long microchipId = rs.getLong("microchip_id");
                if (!rs.wasNull()) {
                    entities.Microchip microchip = new entities.Microchip();
                    microchip.setId(microchipId);
                    mascota.setMicrochip(microchip);
                } else {
                    mascota.setMicrochip(null);
                }

                mascotas.add(mascota);
            }
        }

        return mascotas;
    }

    @Override
    public void actualizar(Mascota entidad) throws Exception {

        if (entidad.getId() == null) {
            throw new IllegalArgumentException("La mascota debe tener id para poder actualizarla");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            // 1) eliminado
            boolean eliminado = entidad.getEliminado() != null ? entidad.getEliminado() : false;
            ps.setBoolean(1, eliminado);

            // 2) nombre
            ps.setString(2, entidad.getNombre());

            // 3) especie
            ps.setString(3, entidad.getEspecie());

            // 4) raza
            ps.setString(4, entidad.getRaza());

            // 5) fecha_nacimiento
            if (entidad.getFechaNacimiento() != null) {
                ps.setDate(5, Date.valueOf(entidad.getFechaNacimiento()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            // 6) duenio
            ps.setString(6, entidad.getDuenio());

            // 7) microchip_id
            if (entidad.getMicrochip() != null && entidad.getMicrochip().getId() != null) {
                ps.setLong(7, entidad.getMicrochip().getId());
            } else {
                ps.setNull(7, Types.BIGINT);
            }

            // 8) id en el WHERE
            ps.setLong(8, entidad.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {

        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null para eliminar");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_LOGICO_SQL)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        }
    }

    // Acá vamos a escribir los métodos del DAO
}

