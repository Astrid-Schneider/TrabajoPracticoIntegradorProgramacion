/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/tpibasedatos";
    private static final String USER = "root"; 
    private static final String PASSWORD = "2806"; // <-- CAMBIAR si tu clave NO es root

    public static Connection getConnection() throws SQLException {
        // Validación adicional para asegurarse de que las credenciales no estén vacías
        if (URL == null || URL.isEmpty() || USER == null || USER.isEmpty() || PASSWORD == null || PASSWORD.isEmpty()) {
            throw new SQLException("Configuración de la base de datos incompleta o inválida");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}