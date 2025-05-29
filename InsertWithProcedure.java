import java.sql.*;
import java.util.Scanner;

public class InsertWithProcedure {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:XE"; // Para Oracle
        String user = "usuario";
        String password = "contraseña";
        
        try (Scanner scanner = new Scanner(System.in);
             Connection conn = DriverManager.getConnection(url, user, password)) {
            
            System.out.println("Ingrese el nombre (deje vacío para NULL):");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) nombre = null;
            
            System.out.println("Ingrese la edad (deje vacío para NULL):");
            String edadInput = scanner.nextLine().trim();
            Integer edad = edadInput.isEmpty() ? null : Integer.parseInt(edadInput);
            
            // Llamar al procedimiento almacenado
            try (CallableStatement cstmt = conn.prepareCall("{call insertar_dato(?, ?, ?, ?)}")) {
                // Parámetros de entrada
                if (nombre == null) {
                    cstmt.setNull(1, Types.VARCHAR);
                } else {
                    cstmt.setString(1, nombre);
                }
                
                if (edad == null) {
                    cstmt.setNull(2, Types.INTEGER);
                } else {
                    cstmt.setInt(2, edad);
                }
                
                // Registrar parámetros de salida
                cstmt.registerOutParameter(3, Types.INTEGER); // status
                cstmt.registerOutParameter(4, Types.VARCHAR); // mensaje
                
                // Ejecutar el procedimiento
                cstmt.execute();
                
                // Obtener resultados
                int status = cstmt.getInt(3);
                String mensaje = cstmt.getString(4);
                
                if (status == 0) {
                    System.out.println("Éxito: " + mensaje);
                } else {
                    System.out.println("Error (" + status + "): " + mensaje);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: La edad debe ser un número entero válido");
        }
    }
}
