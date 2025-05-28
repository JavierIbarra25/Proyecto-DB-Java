package t5_employees;

import java.sql.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Cargar driver y conectar
        if (!mysqlmanager.loadDriver() || !mysqlmanager.connect()) {
            System.err.println("No se pudo conectar a la base de datos");
            return;
        }

        try {
            // Ejemplo de uso de los procedimientos almacenados
            
            // 1. Insertar un nuevo empleado
            System.out.println("\n--- INSERTAR EMPLEADO ---");
            insertarEmpleado(3000, "Juan Pérez", "Desarrollador", 45000, 5, null, 1008);
            
            // 2. Obtener información de un empleado
            System.out.println("\n--- OBTENER EMPLEADO ---");
            obtenerEmpleado(3000);
            
            // 3. Actualizar un empleado
            System.out.println("\n--- ACTUALIZAR EMPLEADO ---");
            actualizarEmpleado(3000, "Juan Pérez", "Senior Developer", 55000, 5, null, 1008);
            
            // 4. Eliminar un empleado
            System.out.println("\n--- ELIMINAR EMPLEADO ---");
            eliminarEmpleado(3000);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Desconectar
            mysqlmanager.disconnect();
        }
    }

    // Método para insertar un empleado
    private static void insertarEmpleado(int codigo, String nombre, String puesto, int salario, 
                                       Integer departamento, LocalDate fechaInicio, Integer superior) throws SQLException {
        try (CallableStatement stmt = mysqlmanager.conn.prepareCall(
                "{CALL insertar_staff(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            
            // Parámetros de entrada
            stmt.setInt(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, puesto);
            stmt.setInt(4, salario);
            if (departamento != null) {
                stmt.setInt(5, departamento);
            } else {
                stmt.setNull(5, Types.SMALLINT);
            }
            if (fechaInicio != null) {
                stmt.setDate(6, Date.valueOf(fechaInicio));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            if (superior != null) {
                stmt.setInt(7, superior);
            } else {
                stmt.setNull(7, Types.SMALLINT);
            }
            
            // Registrar parámetros de salida
            stmt.registerOutParameter(8, Types.SMALLINT);
            stmt.registerOutParameter(9, Types.VARCHAR);
            stmt.registerOutParameter(10, Types.VARCHAR);
            stmt.registerOutParameter(11, Types.SMALLINT);
            stmt.registerOutParameter(12, Types.SMALLINT);
            stmt.registerOutParameter(13, Types.DATE);
            stmt.registerOutParameter(14, Types.SMALLINT);
            stmt.registerOutParameter(15, Types.INTEGER);
            stmt.registerOutParameter(16, Types.VARCHAR);
            
            // Ejecutar procedimiento
            stmt.execute();
            
            // Mostrar resultados
            System.out.println("Status: " + stmt.getInt(15));
            System.out.println("Mensaje: " + stmt.getString(16));
            System.out.println("Empleado insertado: " + stmt.getInt(8) + " - " + stmt.getString(9));
        }
    }

    // Método para obtener información de un empleado
    private static void obtenerEmpleado(int codigo) throws SQLException {
        try (CallableStatement stmt = mysqlmanager.conn.prepareCall(
                "{CALL obtener_staff(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            
            // Parámetro de entrada
            stmt.setInt(1, codigo);
            
            // Registrar parámetros de salida
            stmt.registerOutParameter(2, Types.SMALLINT);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.registerOutParameter(5, Types.SMALLINT);
            stmt.registerOutParameter(6, Types.SMALLINT);
            stmt.registerOutParameter(7, Types.DATE);
            stmt.registerOutParameter(8, Types.SMALLINT);
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.registerOutParameter(10, Types.VARCHAR);
            
            // Ejecutar procedimiento
            stmt.execute();
            
            // Mostrar resultados
            System.out.println("Status: " + stmt.getInt(9));
            System.out.println("Mensaje: " + stmt.getString(10));
            
            if (stmt.getInt(9) == 0) {
                System.out.println("Información del empleado:");
                System.out.println("Código: " + stmt.getInt(2));
                System.out.println("Nombre: " + stmt.getString(3));
                System.out.println("Puesto: " + stmt.getString(4));
                System.out.println("Salario: " + stmt.getInt(5));
                System.out.println("Departamento: " + stmt.getInt(6));
                System.out.println("Fecha inicio: " + stmt.getDate(7));
                System.out.println("Superior: " + stmt.getInt(8));
            }
        }
    }

    // Método para actualizar un empleado
    private static void actualizarEmpleado(int codigo, String nombre, String puesto, int salario, 
                                         Integer departamento, LocalDate fechaInicio, Integer superior) throws SQLException {
        try (CallableStatement stmt = mysqlmanager.conn.prepareCall(
                "{CALL actualizar_staff(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            
            // Parámetros de entrada
            stmt.setInt(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, puesto);
            stmt.setInt(4, salario);
            if (departamento != null) {
                stmt.setInt(5, departamento);
            } else {
                stmt.setNull(5, Types.SMALLINT);
            }
            if (fechaInicio != null) {
                stmt.setDate(6, Date.valueOf(fechaInicio));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            if (superior != null) {
                stmt.setInt(7, superior);
            } else {
                stmt.setNull(7, Types.SMALLINT);
            }
            
            // Registrar parámetros de salida
            stmt.registerOutParameter(8, Types.SMALLINT);
            stmt.registerOutParameter(9, Types.VARCHAR);
            stmt.registerOutParameter(10, Types.VARCHAR);
            stmt.registerOutParameter(11, Types.SMALLINT);
            stmt.registerOutParameter(12, Types.SMALLINT);
            stmt.registerOutParameter(13, Types.DATE);
            stmt.registerOutParameter(14, Types.SMALLINT);
            stmt.registerOutParameter(15, Types.INTEGER);
            stmt.registerOutParameter(16, Types.VARCHAR);
            
            // Ejecutar procedimiento
            stmt.execute();
            
            // Mostrar resultados
            System.out.println("Status: " + stmt.getInt(15));
            System.out.println("Mensaje: " + stmt.getString(16));
            System.out.println("Empleado actualizado: " + stmt.getInt(8) + " - " + stmt.getString(9));
        }
    }

    // Método para eliminar un empleado
    private static void eliminarEmpleado(int codigo) throws SQLException {
        try (CallableStatement stmt = mysqlmanager.conn.prepareCall(
                "{CALL eliminar_staff(?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {
            
            // Parámetro de entrada
            stmt.setInt(1, codigo);
            
            // Registrar parámetros de salida
            stmt.registerOutParameter(2, Types.SMALLINT);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.registerOutParameter(5, Types.SMALLINT);
            stmt.registerOutParameter(6, Types.SMALLINT);
            stmt.registerOutParameter(7, Types.DATE);
            stmt.registerOutParameter(8, Types.SMALLINT);
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.registerOutParameter(10, Types.VARCHAR);
            
            // Ejecutar procedimiento
            stmt.execute();
            
            // Mostrar resultados
            System.out.println("Status: " + stmt.getInt(9));
            System.out.println("Mensaje: " + stmt.getString(10));
            
            if (stmt.getInt(9) == 0) {
                System.out.println("Empleado eliminado:");
                System.out.println("Código: " + stmt.getInt(2));
                System.out.println("Nombre: " + stmt.getString(3));
                System.out.println("Puesto: " + stmt.getString(4));
                System.out.println("Salario: " + stmt.getInt(5));
                System.out.println("Departamento: " + stmt.getInt(6));
                System.out.println("Fecha inicio: " + stmt.getDate(7));
                System.out.println("Superior: " + stmt.getInt(8));
            }
        }
    }
}