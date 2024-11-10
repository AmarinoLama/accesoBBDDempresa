package edu.badpals.Conexion;

import edu.badpals.Tablas.Departamento;
import edu.badpals.Tablas.Proxecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            int count = 0;
            while (rs.next()) {
                System.out.println("Nome: " + rs.getString("nome") + ", apellidos: " + rs.getString("apelido_1") + " " + rs.getString("apelido_2") +
                        ", localidade: " + rs.getString("localidade") + ", salario: " + rs.getDouble("salario") + ", data nacemento: " + rs.getDate("data_nacemento") +
                        " supervisor: " + rs.getInt("nss_supervisa") + ", departamento: " + rs.getInt("num_departamento_pertenece"));
                count++;
            }
            if (count == 0) {
                System.out.println("No se encontraron empleados en " + localidad);
            }
            rs.close();
            s.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Exercicio 2.3. Actualización de datos utilizando a interface PreparedStatement */

    public static void modifDepartamentoProxecto(String nomDepartamento, String nomProxecto) {
        try {
            String query = "UPDATE proxecto SET num_departamento = " +
                    "(SELECT num_departamento FROM departamento WHERE nome_departamento = ?) WHERE nome_proxecto = ?";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, nomDepartamento);
            ps.setString(2, nomProxecto);
            int rowsAfected = ps.executeUpdate();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addProyecto(Proxecto proxecto) {
        try {
            String query = "INSERT INTO proxecto (num_proxecto, nome_proxecto, lugar, num_departamento) VALUES (?, ?, ?, ?)";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, proxecto.getNum_proxecto());
            ps.setString(2, proxecto.getNome_proxecto());
            ps.setString(3, proxecto.getLugar());
            ps.setInt(4, proxecto.getNum_departamento());
            int rowsAfected = ps.executeUpdate();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteProyecto(Integer numProyecto) {
        try {
            String query = "DELETE FROM proxecto WHERE num_proxecto = ?";
            String query2 = "DELETE FROM empregado_proxecto WHERE num_proxecto = ?";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            PreparedStatement ps2 = conexion.prepareStatement(query2);
            ps.setInt(1, numProyecto);
            ps2.setInt(1, numProyecto);
            int rowsAfected2 = ps2.executeUpdate();
            int rowsAfected = ps.executeUpdate();
            ps.close();
            ps2.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /* Exercicio 2.4. Consulta de datos utilizando a interface PreparedStatement */

    public static List<Proxecto> getProxectosByDepartamento(String nombreDepartamento) {
        List<Proxecto> listaProyectos = new ArrayList<>();
        try {
            String query = "SELECT * FROM proxecto WHERE num_departamento = (SELECT num_departamento FROM departamento WHERE nome_departamento = ?)";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, nombreDepartamento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listaProyectos.add(new Proxecto(rs.getInt("num_proxecto"), rs.getString("nome_proxecto"), rs.getString("lugar"), rs.getInt("num_departamento")));
            }
            if (listaProyectos.isEmpty()) {
                System.out.println("No se encontraron proyectos en el departamento " + nombreDepartamento);
            }
            rs.close();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaProyectos;
    }

    /* Exercicio 2.5. Execución de procedementos almacenados e funcións */

}
