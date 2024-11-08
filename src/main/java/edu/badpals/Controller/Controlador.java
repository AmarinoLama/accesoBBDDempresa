package edu.badpals.Controller;

import edu.badpals.Conexion.Conexion;
import edu.badpals.Tablas.Departamento;

import java.time.LocalDate;
import java.util.Scanner;

public class Controlador {

    private static Scanner sc = new Scanner(System.in);

    /* Exercicio 2.1: Actualización de datos utilizando a interface Statement */

    public static void subirSalarioEmpleados() {
        System.out.println("Introduce el salario a subir");
        double salarioSubir = Double.parseDouble(sc.nextLine());
        System.out.println("Introduce el departamento");
        String departamento = sc.nextLine();
        Conexion.subirSalarioEmpleados(salarioSubir, departamento);
    }

    public static void addDepartamento() {
        System.out.println("Introduce el número de departamento");
        int numDepartamento = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el nombre del departamento");
        String nombreDepartamento = sc.nextLine();
        System.out.println("Introduce el nss del director");
        String nss = sc.nextLine();
        LocalDate fechaHoy = LocalDate.now();
        Conexion.addDepartamento(new Departamento(numDepartamento, nombreDepartamento, nss, fechaHoy.toString()));
    }

    public static void deleteEmpleadoProyecto() {
        System.out.println("Introduce el nss del empleado");
        int nss = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el número del proyecto");
        int numProyecto = Integer.parseInt(sc.nextLine());
        Conexion.deleteEmpleadoProyecto(nss, numProyecto);
    }

    /* Exercicio 2.2. Consulta de datos utilizando a interface Statement */

    public static void getEmpleadosByLocalidad() {
        System.out.println("Introduce la localidad");
        String localidad = sc.nextLine();
        Conexion.getEmpleadosByLocalidad(localidad);
    }
}
