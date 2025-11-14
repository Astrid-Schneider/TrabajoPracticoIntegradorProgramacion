/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import entities.Microchip;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MicrochipDao implements GenericDao<Microchip> {
        private static final String INSERT_SQL =
            "INSERT INTO microchip (eliminado, codigo, fecha_implantacion, veterinaria, observaciones) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, eliminado, codigo, fecha_implantacion, veterinaria, observaciones " +
            "FROM microchip " +
            "WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, eliminado, codigo, fecha_implantacion, veterinaria, observaciones " +
            "FROM microchip " +
            "WHERE eliminado = 0";

    private static final String UPDATE_SQL =
            "UPDATE microchip " +
            "SET eliminado = ?, codigo = ?, fecha_implantacion = ?, veterinaria = ?, observaciones = ? " +
            "WHERE id = ?";

    private static final String DELETE_LOGICO_SQL =
            "UPDATE microchip " +
            "SET eliminado = 1 " +
            "WHERE id = ?";

    // despues van los metodos

    @Override
    public void crear(Microchip entidad) throws Exception {

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            boolean eliminado = entidad.getEliminado() != null ? entidad.getEliminado() : false;
            ps.setBoolean(1, eliminado);

            ps.setString(2, entidad.getCodigo());

            if (entidad.getFechaImplantacion() != null) {
                ps.setDate(3, Date.valueOf(entidad.getFechaImplantacion()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            ps.setString(4, entidad.getVeterinaria());
            ps.setString(5, entidad.getObservaciones());

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
    public Microchip leer(Long id) throws Exception {

        Microchip microchip = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    microchip = new Microchip();

                    microchip.setId(rs.getLong("id"));
                    microchip.setEliminado(rs.getBoolean("eliminado"));
                    microchip.setCodigo(rs.getString("codigo"));

                    Date fechaSql = rs.getDate("fecha_implantacion");
                    if (fechaSql != null) {
                        microchip.setFechaImplantacion(fechaSql.toLocalDate());
                    }

                    microchip.setVeterinaria(rs.getString("veterinaria"));
                    microchip.setObservaciones(rs.getString("observaciones"));
                }
            }
        }

        return microchip;
    }

    @Override
    public List<Microchip> leerTodos() throws Exception {

        List<Microchip> microchips = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Microchip microchip = new Microchip();

                microchip.setId(rs.getLong("id"));
                microchip.setEliminado(rs.getBoolean("eliminado"));
                microchip.setCodigo(rs.getString("codigo"));

                Date fechaSql = rs.getDate("fecha_implantacion");
                if (fechaSql != null) {
                    microchip.setFechaImplantacion(fechaSql.toLocalDate());
                }

                microchip.setVeterinaria(rs.getString("veterinaria"));
                microchip.setObservaciones(rs.getString("observaciones"));

                microchips.add(microchip);
            }
        }

        return microchips;
    }

    @Override
    public void actualizar(Microchip entidad) throws Exception {

        if (entidad.getId() == null) {
            throw new IllegalArgumentException("El microchip debe tener id para poder actualizarlo");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            boolean eliminado = entidad.getEliminado() != null ? entidad.getEliminado() : false;
            ps.setBoolean(1, eliminado);

            ps.setString(2, entidad.getCodigo());

            if (entidad.getFechaImplantacion() != null) {
                ps.setDate(3, Date.valueOf(entidad.getFechaImplantacion()));
            } else {
                ps.setNull(3, Types.DATE);
            }

            ps.setString(4, entidad.getVeterinaria());
            ps.setString(5, entidad.getObservaciones());

            ps.setLong(6, entidad.getId());

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
}
