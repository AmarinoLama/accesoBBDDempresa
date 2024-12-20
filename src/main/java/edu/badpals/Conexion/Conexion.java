package edu.badpals.Conexion;

import edu.badpals.Tablas.Departamento;
import edu.badpals.Tablas.Proxecto;

import javax.print.DocFlavor;
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

    public static void pr_cambioDomicilio(String nss, String rua, Integer numero, String piso, String cp, String localidad) {
        try {
            String query = "{CALL pr_cambioDomicilio(?, ?, ?, ?, ?, ?)}";
            Connection conexion = connectDatabase();
            CallableStatement cs = conexion.prepareCall(query);
            cs.setString(1, nss);
            cs.setString(2, rua);
            cs.setInt(3, numero);
            cs.setString(4, piso);
            cs.setString(5, cp);
            cs.setString(6, localidad);
            cs.execute();
            cs.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Proxecto pr_DatosProxectos(Integer numProxecto) {
        Proxecto proxecto = null;
        try {
            String query = "{CALL pr_DatosProxectos(?, ?, ?, ?)}";
            Connection conexion = connectDatabase();
            CallableStatement cs = conexion.prepareCall(query);

            // Parámetros de entrada
            cs.setInt(1, numProxecto);

            // Parámetros de salida
            cs.registerOutParameter(2, java.sql.Types.VARCHAR); // nome_proxecto_salida
            cs.registerOutParameter(3, java.sql.Types.VARCHAR); // lugar_salida
            cs.registerOutParameter(4, java.sql.Types.INTEGER); // num_departamento_salida

            // LLamamos al procedimiento
            cs.execute();

            // Salida de datos
            proxecto = new Proxecto(numProxecto, cs.getString(2), cs.getString(3), cs.getInt(4));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return proxecto;
    }

    public static void pr_DepartControlaProxec(Integer numProxectosBuscar) {
        try {
            String query = "{CALL pr_DepartControlaProxec(?)}";
            Connection conexion = connectDatabase();
            CallableStatement cs = conexion.prepareCall(query);

            // Parámetro de entrada
            cs.setInt(1, numProxectosBuscar);

            // Llamamos al procedimiento
            ResultSet rs = cs.executeQuery();

            // Procesamos los resultados
            while (rs.next()) {
                int numDepartamento = rs.getInt("num_departamento");
                String nomeDepartamento = rs.getString("nome_departamento");
                String nssDirige = rs.getString("nss_dirige");
                Date dataDireccion = rs.getDate("data_direccion");

                System.out.println("Num departamento: " + numDepartamento + " / Nome departamento: "
                        + nomeDepartamento + " / Nss dirige: " + nssDirige + " / Data dirección: " + dataDireccion);
            }

            rs.close();
            cs.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void fn_nEmpDepart(String nombreDept) {
        try {
            String query = "SELECT (fn_nEmpDepart(?))";
            Connection conexion = connectDatabase();
            CallableStatement cs = conexion.prepareCall(query);

            cs.setString(1, nombreDept);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                System.out.println("Número de empleados en el departamento " + rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* Exercicio 2.6. Xestión do resultado dunha consulta */

    public static void tiposResulset() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("Database Product Name: " + dbmd.getDatabaseProductName());
            System.out.println("Database Product Version: " + dbmd.getDatabaseProductVersion());
            System.out.println("Database Major Version: " + dbmd.getDatabaseMajorVersion());
            System.out.println("Database Minor Version: " + dbmd.getDatabaseMinorVersion());
            System.out.println("Driver Name: " + dbmd.getDriverName());
            System.out.println("Driver Major Version: " + dbmd.getDriverMajorVersion());
            System.out.println("Driver Minor Version: " + dbmd.getDriverMinorVersion());
            System.out.println("Driver Version: " + dbmd.getDriverVersion());
            System.out.println("URL: " + dbmd.getURL());
            System.out.println("User Name: " + dbmd.getUserName());
            System.out.println("Is Read Only: " + dbmd.isReadOnly());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void insertProyectoAvanzado(Proxecto proxecto) {
        try {
            if (!existProyecto(proxecto.getNum_proxecto(), proxecto.getNome_proxecto()) && existDept(proxecto.getNum_departamento())) {
                String query = "INSERT INTO proxecto (num_proxecto, nome_proxecto, lugar, num_departamento) VALUES (?, ?, ?, ?)";
                Connection conexion = connectDatabase();
                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setInt(1, proxecto.getNum_proxecto());
                ps.setString(2, proxecto.getNome_proxecto());
                ps.setString(3, proxecto.getLugar());
                ps.setInt(4, proxecto.getNum_departamento());
            } else if (existProyecto(proxecto.getNum_proxecto(), proxecto.getNome_proxecto())) {
                System.out.println("No se puede crear el proyecto porque su número o nombre se repite");
            } else if (!existDept(proxecto.getNum_departamento())) {
                System.out.println("No se puede crear el proyecto porque el departamento al que referencia no existe");
            } else {
                System.out.println("Error al crearse el proyecto");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existProyecto(Integer numProyecto, String nomeProxecto) {
        try {
            String query = "SELECT * FROM proxecto WHERE num_proxecto = ? AND nome_proxecto = ?";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, numProyecto);
            ps.setString(2, nomeProxecto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean existDept(Integer numDept) {
        try {
            String query = "SELECT * FROM departamento WHERE num_departamento = ?";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setInt(1, numDept);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void subirSalarioDept(Integer numDept, double salarioSubir) {
        try {
            String query = "UPDATE empregado SET salario = salario + ? WHERE num_departamento_pertenece = ?";
            Connection conexion = connectDatabase();
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setDouble(1, salarioSubir);
            ps.setInt(2, numDept);
            int rowsAfected = ps.executeUpdate();
            ps.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet datosEmpregadosNproxecto(Integer numProxectos) {
        try {
            String query = """
            SELECT e.nss, CONCAT(e.nome, ' ', e.apelido_1, ' ', e.apelido_2) AS nome_completo, e.localidade, e.salario
                FROM empregado e
                JOIN empregado_proxecto pe ON e.nss = pe.nss_empregado
                GROUP BY e.nss, e.nome, e.apelido_1, e.apelido_2, e.localidade, e.salario
                HAVING COUNT(pe.num_proxecto) > ?;
            """;

            Connection conexion = connectDatabase();
            // Crear el PreparedStatement con desplazamiento bidireccional
            PreparedStatement ps = conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, numProxectos);
            ResultSet rs = ps.executeQuery();

            return rs;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /* Exercicio 3.1. Obtención de información sobre o SXBD e a conexión */

    public static void infoConexion() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("Database Product Name: " + dbmd.getDatabaseProductName());
            System.out.println("Database Product Version: " + dbmd.getDatabaseProductVersion());
            System.out.println("Database Major Version: " + dbmd.getDatabaseMajorVersion());
            System.out.println("Database Minor Version: " + dbmd.getDatabaseMinorVersion());
            System.out.println("Driver Name: " + dbmd.getDriverName());
            System.out.println("Driver Major Version: " + dbmd.getDriverMajorVersion());
            System.out.println("Driver Minor Version: " + dbmd.getDriverMinorVersion());
            System.out.println("Driver Version: " + dbmd.getDriverVersion());
            System.out.println("URL: " + dbmd.getURL());
            System.out.println("User Name: " + dbmd.getUserName());
            System.out.println("Is Read Only: " + dbmd.isReadOnly());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* Exercicio 3.2. Acceso aos metadatos referente as táboas e os procedementos */

    public static void viewTablas() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet tables = dbmd.getTables("bbdd_empresa", null, null, new String[]{"TABLE"});
            while (tables.next()) {
                System.out.println("Tabla: " + tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readColumnsEschema(String eschema, String tabla) {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columns = dbmd.getColumns(eschema, null, tabla, null);
            while (columns.next()) {
                System.out.println("Columna: " + columns.getString("COLUMN_NAME") + ", Tipo de datos: " + columns.getString("TYPE_NAME") +
                        ", Tamaño: " + columns.getInt("COLUMN_SIZE") + ", Admite nulos: " + columns.getString("IS_NULLABLE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewProcedures() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet procedures = dbmd.getProcedures("bbdd_empresa", null, null);
            while (procedures.next()) {
                System.out.println("Procedimiento: " + procedures.getString("PROCEDURE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readPKEschema(String eschema, String tabla) {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columns = dbmd.getPrimaryKeys(eschema, null, tabla);
            while (columns.next()) {
                System.out.println("Clave primaria: " + columns.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readFKEschema(String eschema, String tabla) {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columns = dbmd.getExportedKeys(eschema, null, tabla);
            while (columns.next()) {
                System.out.println("Clave foránea: " + columns.getString("FKCOLUMN_NAME") + ", Tabla referencia: " + columns.getString("PKTABLE_NAME") +
                        ", Columna referencia: " + columns.getString("PKCOLUMN_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* Exercicio 3.3. Acceso aos metadatos referente as funcións, procedementos e características dispoñibles do SXBD */

    public static void infoConexionMejorado() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("Palabras clave SQL: " + dbmd.getSQLKeywords());
            System.out.println("Cadena de comillas identificadoras: " + dbmd.getIdentifierQuoteString());
            System.out.println("Cadena de escape de búsqueda: " + dbmd.getSearchStringEscape());
            System.out.println("Funciones numéricas: " + dbmd.getNumericFunctions());
            System.out.println("Funciones de cadena: " + dbmd.getStringFunctions());
            System.out.println("Funciones de fecha y hora: " + dbmd.getTimeDateFunctions());
            System.out.println("Funciones del sistema: " + dbmd.getSystemFunctions());
            System.out.println("¿Todos los procedimientos son llamables?: " + dbmd.allProceduresAreCallable());
            System.out.println("¿Todas las tablas son seleccionables?: " + dbmd.allTablesAreSelectable());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* Exercicio 3.4. Acceso aos metadatos dos límites impostos polo conectador */

    public static void infoConexionMasMejorado() { /* todo mejorar salida datos */
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("Número máximo de conexiones: " + dbmd.getMaxConnections());
            System.out.println("Número máximo de sentencias: " + dbmd.getMaxStatements());
            System.out.println("Número máximo de tablas en una selección: " + dbmd.getMaxTablesInSelect());
            System.out.println("Longitud máxima del nombre de una tabla: " + dbmd.getMaxTableNameLength());
            System.out.println("Longitud máxima del nombre de una columna: " + dbmd.getMaxColumnNameLength());
            System.out.println("Longitud máxima de una sentencia: " + dbmd.getMaxStatementLength());
            System.out.println("Longitud máxima del nombre de un procedimiento: " + dbmd.getMaxProcedureNameLength());
            System.out.println("Número máximo de columnas en un GROUP BY: " + dbmd.getMaxColumnsInGroupBy());
            System.out.println("Número máximo de columnas en un ORDER BY: " + dbmd.getMaxColumnsInOrderBy());
            System.out.println("Número máximo de columnas en una selección: " + dbmd.getMaxColumnsInSelect());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* Exercicio 3.5. Metadatos sobre as transaccións */

    public static void infoConexionOtro() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("Soporta transaccións: " + dbmd.supportsTransactions());
            System.out.println("Nivel illamiento de las transaccións: " + dbmd.getDefaultTransactionIsolation());
            System.out.println("Soportan sentenzas de manipulación de datos y de definición de datos dentro de las transaccións: " + dbmd.supportsDataDefinitionAndDataManipulationTransactions());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* Exercicio 3.6. Métodos sobre o soporte de características */

    public static void infoOrdenes() {
        try {
            Connection conexion = connectDatabase();
            DatabaseMetaData dbmd = conexion.getMetaData();
            System.out.println("Soporta ADD COLUMN en ALTER TABLE: " + dbmd.supportsAlterTableWithAddColumn());
            System.out.println("Soporta DROP COLUMN en ALTER TABLE: " + dbmd.supportsAlterTableWithDropColumn());
            System.out.println("En los alias de columnas puede usarse la palabra AS: " + dbmd.supportsColumnAliasing());
            System.out.println("Devolve true si o resultado de concatenar un NULL e un NOT NULL resulta NULL: " + dbmd.nullPlusNonNullIsNull());
            System.out.println("Devolve true si se soportan as conversións entre tipos de datos JDBC: " + dbmd.supportsConvert());
            System.out.println("Devolve true si se soportan os nomes de táboas correlacionadas: " + dbmd.supportsTableCorrelationNames());
            System.out.println("Devolve true si si se soporta o uso dunha columna que non está na instrución SELECT " +
                    "nunha cláusula ORDER BY: " + dbmd.supportsOrderByUnrelated ());
            System.out.println("Devolve true si se soporta a cláusula GROUP BY: " + dbmd.supportsGroupBy());
            System.out.println("Devolve true si se admite o uso dunha columna que non está na instrución SELECT nunha" +
                    "cláusula GROUP BY: " + dbmd.supportsGroupByUnrelated());
            System.out.println("Devolve true si se soporta a cláusula LIKE: " + dbmd.supportsLikeEscapeClause());
            System.out.println("Devolve true si se soporta os outer joins: " + dbmd.supportsOuterJoins());
            System.out.println("Devolve true si se soporta as subconsultas EXIST: " + dbmd.supportsSubqueriesInExists());
            System.out.println("Devolve true si se soporta as subconsultas nas expresións de comparación: " + dbmd.supportsSubqueriesInComparisons());
            System.out.println("Devolve true si se soporta as subconsultas nas expresións IN: " + dbmd.supportsSubqueriesInIns());
            System.out.println("Devolve true si se soporta as subconsultas nas expresións cuantificativas: " + dbmd.supportsSubqueriesInQuantifieds());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* Exercicio 3.7. Metadatos do ResultSet */

    public static void metadatosResulset(String query) {
        try {
            Connection conexion = connectDatabase();
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println("Número de columnas: " + rsmd.getColumnCount());
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.println("Nombre columna: " + rsmd.getColumnName(i) + ", Tipo de datos: " + rsmd.getColumnTypeName(i) +
                        ", Tamaño: " + rsmd.getColumnDisplaySize(i) + ", Admite nulos: " + rsmd.isNullable(i));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}