
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    
    private static final String url = "jdbc:mysql://localhost:3306/leiloestdsat?useSSL=false";
    private static final String user = "root";
    private static final String senha = "root";

    public Connection connectDB() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, senha);

        } catch (SQLException e) {
            System.out.println("Falha na conexaoDao " + e.getMessage());

        }
        return conn;
    }
    
}
