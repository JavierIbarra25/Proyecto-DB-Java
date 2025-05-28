package t5_employees;

import java.sql.*;
import java.time.LocalDate;
import java.sql.JDBCType;

public class StaffManager {
    // Constantes de la tabla
    private final String TB_STAFF_CODE = "Employee_Code";
    private final String TB_STAFF_NAME = "Name";
    private final String TB_STAFF_JOB = "Job";
    private final String TB_STAFF_SALARY = "Salary";
    private final String TB_STAFF_DEPTO = "Department_Code";
    private final String TB_STAFF_START = "Start_Date";
    private final String TB_STAF_SUPOFF = "Superior_Officer";
    private final String TB_STAFF = "staff";
    private final String TB_STAFF_SELECT = "SELECT * FROM " + TB_STAFF;

    // Método para obtener staff mediante SQL directo
    private ResultSet getStaff(int id) {
        try {
            Statement stmt = mysqlmanager.conn.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_READ_ONLY);
            String sql = TB_STAFF_SELECT + " WHERE " + TB_STAFF_CODE + "=" + id;
            System.out.println("[DEBUG] Ejecutando: " + sql);
            return stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.err.println("Error al obtener staff: " + ex.getMessage());
            return null;
        }
    }

    // Método público para imprimir información de staff
    public void imprimeStaff(int id) {
        try {
            ResultSet rs = this.getStaff(id);
            
            if (rs == null || !rs.first()) {
                System.out.println("Staff " + id + " NO EXISTE");
                return;
            }

            int sid = rs.getInt(TB_STAFF_CODE);
            String nombre = rs.getString(TB_STAFF_NAME);
            String job = rs.getString(TB_STAFF_JOB);
            int salary = rs.getInt(TB_STAFF_SALARY);
            int depto = rs.getInt(TB_STAFF_DEPTO);
            String start = rs.getString(TB_STAFF_START);
            int jefe = rs.getInt(TB_STAF_SUPOFF);

            System.out.println("Staff: " + sid + "\t" + nombre + "\t" + job + 
                             "\t" + salary + "\t" + depto + "\t" + start + "\t" + jefe);
            
        } catch(SQLException ex) {
            System.err.println("Error al imprimir staff: " + ex.getMessage());
        }
    }

    // Método para llamar al procedimiento almacenado de obtener staff
    public void obtenerStaffSP(int id) {
        try {
            String sql = "{call obtener_staff(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cStmt = mysqlmanager.conn.prepareCall(sql);

            // Parámetro de entrada
            cStmt.setInt(1, id);

            // Registrar parámetros de salida
            cStmt.registerOutParameter(2, JDBCType.SMALLINT);
            cStmt.registerOutParameter(3, JDBCType.VARCHAR);
            cStmt.registerOutParameter(4, JDBCType.VARCHAR);
            cStmt.registerOutParameter(5, JDBCType.SMALLINT);
            cStmt.registerOutParameter(6, JDBCType.SMALLINT);
            cStmt.registerOutParameter(7, JDBCType.DATE);
            cStmt.registerOutParameter(8, JDBCType.SMALLINT);
            cStmt.registerOutParameter(9, JDBCType.INTEGER);
            cStmt.registerOutParameter(10, JDBCType.VARCHAR);

            // Ejecutar
            cStmt.execute();

            // Obtener resultados
            int errorCode = cStmt.getInt(9);
            String errorMsg = cStmt.getString(10);
            
            if (errorCode == 0) {
                System.out.println("Staff (SP): " + 
                    cStmt.getInt(2) + "\t" + 
                    cStmt.getString(3) + "\t" + 
                    cStmt.getString(4) + "\t" + 
                    cStmt.getInt(5) + "\t" + 
                    cStmt.getInt(6) + "\t" + 
                    cStmt.getDate(7) + "\t" + 
                    cStmt.getInt(8));
            } else {
                System.err.println("Error: " + errorCode + " - " + errorMsg);
            }
        } catch(SQLException ex) {
            System.err.println("Error al llamar al procedimiento almacenado: " + ex.getMessage());
        }
    }

    // Métodos adicionales para insertar, actualizar y eliminar (similar al obtener)
    public void insertarStaffSP(int id, String nombre, String puesto, int salario, 
                              Integer depto, LocalDate fechaInicio, Integer superior) {
        // Implementación similar a obtenerStaffSP pero con el procedimiento insertar_staff

        
    }
    
    public void actualizarStaffSP(int id, String nombre, String puesto, int salario, 
                                Integer depto, LocalDate fechaInicio, Integer superior) {
        // Implementación similar a obtenerStaffSP pero con el procedimiento actualizar_staff
    }
    
    public void eliminarStaffSP(int id) {
        // Implementación similar a obtenerStaffSP pero con el procedimiento eliminar_staff
    }
}