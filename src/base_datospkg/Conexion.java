package base_datospkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static String url = "jdbc:mysql://localhost:3407/usuarios";
	private static String usuario = "user";
	private static String password = "RENEram123";
	
	public static Connection conectar() {
		Connection conexion = null;
		try {
			conexion = DriverManager.getConnection(url,usuario,password);
			
		}catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
		}
		return conexion;
	}

}
