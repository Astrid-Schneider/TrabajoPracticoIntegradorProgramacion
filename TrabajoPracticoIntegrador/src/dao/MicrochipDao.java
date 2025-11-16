
package dao;

import config.DatabaseConnection;
import entities.Microchip;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class MicrochipDao implements GenericDao<Microchip> {

    private static final String INSERT_SQL
            = "INSERT INTO microchip (eliminado, codigo, fecha_implantacion, veterinaria, observaciones) "
            + "VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL
            = "SELECT id, eliminado, codigo, fecha_implantacion, veterinaria, observaciones "
            + "FROM microchip "
            + "WHERE id = ?";

    private static final String SELECT_ALL_SQL
            = "SELECT id, eliminado, codigo, fecha_implantacion, veterinaria, observaciones "
            + "FROM microchip "
            + "WHERE eliminado = 0";

    private static final String UPDATE_SQL
            = "UPDATE microchip "
            + "SET eliminado = ?, codigo = ?, fecha_implantacion = ?, veterinaria = ?, observaciones = ? "
            + "WHERE id = ?";

    private static final String DELETE_LOGICO_SQL
            = "UPDATE microchip "
            + "SET eliminado = 1 "
            + "WHERE id = ?";

    // despues van los metodos
    @Override
    public void crear(Microchip microchip) throws Exception {
        // version normal: abre y cierra su propia conexion
        try (Connection con = DatabaseConnection.getConnection()) {
            crear(microchip, con);
        }
    }

// version para usar dentro de una transaccion
    public void crear(Microchip microchip, Connection con) throws Exception {
        String sql = INSERT_SQL;

        try (PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            boolean eliminado = Boolean.TRUE.equals(microchip.getEliminado());
            ps.setBoolean(1, eliminado);
            ps.setString(2, microchip.getCodigo());

            if (microchip.getFechaImplantacion() != null) {
                ps.setDate(3, java.sql.Date.valueOf(microchip.getFechaImplantacion()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            if (microchip.getVeterinaria() != null) {
                ps.setString(4, microchip.getVeterinaria());
            } else {
                ps.setNull(4, java.sql.Types.VARCHAR);
            }

            if (microchip.getObservaciones() != null) {
                ps.setString(5, microchip.getObservaciones());
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);
            }

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    microchip.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Microchip leer(Long id) throws Exception {

        Microchip microchip = null;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

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

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL); ResultSet rs = ps.executeQuery()) {

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

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            boolean eliminado = Boolean.TRUE.equals(entidad.getEliminado());
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
    
    public Microchip buscarPorId(Long id) throws Exception {

    if (id == null) {
        throw new IllegalArgumentException("El id no puede ser null");
    }

    String sql = "SELECT * FROM microchip WHERE id = ? AND eliminado = 0";
    Microchip microchip = null;

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setLong(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                microchip = new Microchip();
                microchip.setId(rs.getLong("id"));
                microchip.setCodigo(rs.getString("codigo"));
                microchip.setVeterinaria(rs.getString("veterinaria"));
                microchip.setObservaciones(rs.getString("observaciones"));

                Date fechaImplantacion = rs.getDate("fecha_implantacion");
                if (fechaImplantacion != null) {
                    microchip.setFechaImplantacion(fechaImplantacion.toLocalDate());
                }

                microchip.setEliminado(rs.getBoolean("eliminado"));
            }
        }
    }

    return microchip;
}


    @Override
    public void eliminar(Long id) throws Exception {

        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser null para eliminar");
        }

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_LOGICO_SQL)) {

            ps.setLong(1, id);

            ps.executeUpdate();
        }
    }
}
