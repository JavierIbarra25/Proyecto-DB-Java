package t5_employees;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class mysqlmanager {

    // Variable para la conexión
    static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "t5_employees"; // Base de datos a la que conectamos
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "ROOT2023$";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    // Carga del driver
    public static boolean loadDriver() {
 
    	try {
    		System.out.print("Cargando driver...");
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		 System.out.println("Ok!");
    		return true;
    	} catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return false;
    		}
    }

    //Conexión a la DDBB
    public static boolean connect() {
    	
    	try {
    		System.out.print("Conectando a la base de datos...");
    		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println(DB_MSQ_CONN_OK);
    		return true;
    	} catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
    		ex.printStackTrace();
    		return false;
    	}
    }

        // Comprueba el estado de la conexión
    public static boolean isConnected() {
		System.out.print("Comprobando conexión a la base de datos...");
    	try {
    		if (conn != null && conn.isValid(0)) {
                System.out.println(DB_MSQ_CONN_OK);
                return true;    			
    		} else {
    			return false;
    		}
    	} catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
    		ex.printStackTrace();
    		return false;
    	}
    }

    // Desconecta de la DDBB
    public static boolean disconnect() {
    	try {
    		System.out.print("Desconectando de la base de datos...");
    		conn.close();
    		System.out.println("Ok!");
    		return true;
    	} catch(SQLException ex) {
   		 	ex.printStackTrace();
    		return false;
    	}
    }
 
}
