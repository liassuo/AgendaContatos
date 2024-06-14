/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author samue_99dfzlt
 */
public class ConexaoPostgreSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/Contato";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "5342";

    public static Connection conectar() throws Exception {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            
        } catch (SQLException e) {
            throw new Exception ("Falha ao conectar ao banco de dados!");
        }
        return conn;
    }

    public static void main(String[] args) throws Exception {
        Connection conn = conectar();
        // Feche a conexão quando não for mais necessário
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

