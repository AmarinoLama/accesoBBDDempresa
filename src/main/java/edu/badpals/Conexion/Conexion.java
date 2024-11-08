package edu.badpals.Conexion;

import edu.badpals.Tablas.Departamento;
import java.sql.*;

public class Conexion {

    private static Connection connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bbdd_empresa";
            return DriverManager.getConnection(url, "root", "root");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /* Exercicio 2.1: Actualización de datos utilizando a interface Statement */

    public static void subirSalarioEmpleados(double salarioSubir, String departamento) {
        try {
            String query = "UPDATE empregado SET salario = salario + " + salarioSubir + " WHERE num_departamento_pertenece = " +
                    "(SELECT DISTINCT num_departamento FROM departamento WHERE nome_departamento = '" + departamento + "')";
            Connection conexion = connectDatabase();
            Statement s = conexion.createStatement();
            int rowsAfected = s.executeUpdate(query);
            s.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addDepartamento(Departamento departamento) {
        try {
            String query = "INSERT INTO departamento (num_departamento, nome_departamento, nss_dirige, data_direccion) VALUES (" +
                    departamento.getNum_departamento() + ", '" + departamento.getNome_departamento() + "', '" + departamento.getNss_dirige() + "', '" + departamento.getData_direccion() + "')";
            Connection conexion = connectDatabase();
            Statement s = conexion.createStatement();
            int rowsAfected = s.executeUpdate(query);
            s.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteEmpleadoProyecto(int nss_empleado, int num_proyecto) {
        try {
            String query = "DELETE FROM empregado_proxecto WHERE nss_empregado = " + nss_empleado + " AND num_proxecto = " + num_proyecto;
            Connection conexion = connectDatabase();
            Statement s = conexion.createStatement();
            int rowsAfected = s.executeUpdate(query);
            s.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Exercicio 2.2. Consulta de datos utilizando a interface Statement */

    public static void getEmpleadosByLocalidad(String localidad) {
        try {
            String query = "SELECT * FROM empregado WHERE localidade = '" + localidad + "'";
            Connection conexion = connectDatabase();
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                System.out.println("Nome: " + rs.getString("nome") + ", apellidos: " + rs.getString("apelido_1") + " " + rs.getString("apelido_2") +
                        ", localidade: " + rs.getString("localidade") + ", salario: " + rs.getDouble("salario") + ", data nacemento: " + rs.getDate("data_nacemento") +
                        " supervisor: " + rs.getInt("nss_supervisor") + ", departamento: " + rs.getInt("num_departamento_pertenece"));
                /* arreglar nombres */
            }
            rs.close();
            s.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
