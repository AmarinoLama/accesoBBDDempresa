package edu.badpals.Controller;

import edu.badpals.Conexion.Conexion;
import edu.badpals.Tablas.Departamento;
import edu.badpals.Tablas.Proxecto;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Controlador {

    private static Scanner sc = new Scanner(System.in);

    /* Exercicio 2.1: Actualización de datos utilizando a interface Statement */

    public static void subirSalarioEmpleados() {
        System.out.println("Introduce el salario a subir");
        double salarioSubir = Double.parseDouble(sc.nextLine());
        System.out.println("Introduce el departamento de empleados que le quieres subir el precio");
        String departamento = sc.nextLine().toUpperCase();
        Conexion.subirSalarioEmpleados(salarioSubir, departamento);
    }

    public static void addDepartamento() {
        System.out.println("Introduce el número del nuevo departamento");
        int numDepartamento = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el nombre del nuevo departamento");
        String nombreDepartamento = sc.nextLine().toUpperCase();
        System.out.println("Introduce el nss del director del nuevo departamento");
        String nss = sc.nextLine().toUpperCase();
        LocalDate fechaHoy = LocalDate.now();
        Conexion.addDepartamento(new Departamento(numDepartamento, nombreDepartamento, nss, fechaHoy.toString()));
    }

    public static void deleteEmpleadoProyecto() {
        System.out.println("Introduce el nss del empleado que quieres borrar en empleados_proyectos");
        int nss = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el número del proyecto que quieras borrar en empleados_proyectos");
        int numProyecto = Integer.parseInt(sc.nextLine());
        Conexion.deleteEmpleadoProyecto(nss, numProyecto);
    }

    /* Exercicio 2.2. Consulta de datos utilizando a interface Statement */

    public static void getEmpleadosByLocalidad() {
        System.out.println("Introduce la localidad de los empleados que quieres buscar");
        String localidad = sc.nextLine().toUpperCase();
        Conexion.getEmpleadosByLocalidad(localidad);
    }

    /* Exercicio 2.3. Actualización de datos utilizando a interface PreparedStatement */

    public static void modifDepartamentoProxecto() {
        System.out.println("Introduce el nombre de departamento del departamento que quieres modificar");
        String nombreDepartamento = sc.nextLine().toUpperCase();
        System.out.println("Introduce el nombre de proyecto en el proyecto que quieres modificar");
        String nombreProyecto = sc.nextLine().toUpperCase();
        Conexion.modifDepartamentoProxecto(nombreDepartamento, nombreProyecto);
    }

    public static void addProyecto() {
        System.out.println("Introduce el número del nuevo proyecto");
        int numProyecto = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el nombre del nuevo proyecto");
        String nombreProyecto = sc.nextLine().toUpperCase();
        System.out.println("Introduce la localidad del nuevo proyecto");
        String localidad = sc.nextLine().toUpperCase();
        System.out.println("Introduce el número de departamento del nuevo proyecto");
        int numDepartamento = Integer.parseInt(sc.nextLine());
        Conexion.addProyecto(new Proxecto(numProyecto, nombreProyecto, localidad, numDepartamento));
    }

    public static void deleteProyecto() {
        System.out.println("Introduce el número del proyecto que quieres borrar");
        int numProyecto = Integer.parseInt(sc.nextLine());
        Conexion.deleteProyecto(numProyecto);
    }

    /* Exercicio 2.4. Inserción de datos utilizando a interface PreparedStatement */

    public static void getProxectosByDepartamento() {
        System.out.println("Introduce el nombre del departamento de los proyectos que quieres filtrar");
        String nombreDepartamento = sc.nextLine().toUpperCase();

        List<Proxecto> proxectos = Conexion.getProxectosByDepartamento(nombreDepartamento);
        for (Proxecto proxecto : proxectos) {
            System.out.println("Num proxecto : " + proxecto.getNum_proxecto() + " / Nome proxecto : " + proxecto.getNome_proxecto() + " / Lugar : " + proxecto.getLugar() + " / Num departamento : " + proxecto.getNum_departamento());
        }
    }

    /* Exercicio 2.5. Execución de procedementos almacenados e funcións */


}
