DROP DATABASE IF EXISTS BBDD_EMPRESA;
CREATE DATABASE IF NOT EXISTS BBDD_EMPRESA;
USE BBDD_EMPRESA;

DROP TABLE IF EXISTS empregado_proxecto;
DROP TABLE IF EXISTS proxecto;
DROP TABLE IF EXISTS empregado;
DROP TABLE IF EXISTS departamento;

CREATE TABLE departamento (
    num_departamento INT NOT NULL,
    nome_departamento VARCHAR(25) NOT NULL,
    nss_dirige VARCHAR(15) NULL,
    data_direccion DATE NULL,
    
    PRIMARY KEY (num_departamento)
) ENGINE=INNODB;

CREATE TABLE empregado (
    nome VARCHAR(25) NOT NULL,
    apelido_1 VARCHAR(25) NOT NULL,
    apelido_2 VARCHAR(25) NOT NULL,
    nss VARCHAR(15) NOT NULL,
    rua VARCHAR(30) NULL,
    numero_rua INT NULL,
    piso VARCHAR(4) NULL,   
    cp CHAR(5) NULL,
    localidade VARCHAR(25),
    data_nacemento DATE NULL,
    salario FLOAT NULL,
    sexo CHAR(1),
    nss_supervisa VARCHAR(15) NULL,
    num_departamento_pertenece INT NULL,
    
    PRIMARY KEY (nss)
) ENGINE=INNODB;

CREATE TABLE proxecto (
    num_proxecto INT NOT NULL,
    nome_proxecto VARCHAR(25) NOT NULL,
    lugar VARCHAR(25) NOT NULL,
    num_departamento INT NOT NULL,
    
    PRIMARY KEY (num_proxecto)
) ENGINE=INNODB;

CREATE TABLE empregado_proxecto (
    nss_empregado VARCHAR(15) NOT NULL,
    num_proxecto INT NOT NULL,
    horas_semanais INT NULL,
    
    PRIMARY KEY (nss_empregado, num_proxecto)
) ENGINE=INNODB;

-- Agregar restricciones y índices únicos

ALTER TABLE empregado 
    ADD CONSTRAINT FK_SUPERVISAR_EMPLEADO FOREIGN KEY (nss_supervisa) REFERENCES empregado(nss),
    ADD CONSTRAINT FK_departamento_pertenece FOREIGN KEY (num_departamento_pertenece) REFERENCES departamento(num_departamento);

ALTER TABLE departamento 
    ADD CONSTRAINT FK_empleado_dirige FOREIGN KEY (nss_dirige) REFERENCES empregado(nss);

ALTER TABLE proxecto 
    ADD CONSTRAINT FK_num_departamento FOREIGN KEY (num_departamento) REFERENCES departamento(num_departamento);

ALTER TABLE empregado_proxecto 
    ADD CONSTRAINT FK_nss_empregado FOREIGN KEY (nss_empregado) REFERENCES empregado(nss),
    ADD CONSTRAINT FK_num_proxecto FOREIGN KEY (num_proxecto) REFERENCES proxecto(num_proxecto);

DELIMITER $$

DROP PROCEDURE IF EXISTS pr_cambioDomicilio $$
CREATE PROCEDURE pr_cambioDomicilio (
    IN nss_empregado VARCHAR(15),
    IN rua_nueva VARCHAR(30),
    IN numero_rua_nueva INT,
    IN piso_nuevo VARCHAR(4),
    IN cp_nuevo CHAR(5),
    IN localidade_nueva VARCHAR(25)
) 
BEGIN
    UPDATE empregado 
    SET 
        rua = rua_nueva, 
        numero_rua = numero_rua_nueva, 
        piso = piso_nuevo, 
        cp = cp_nuevo, 
        localidade = localidade_nueva 
    WHERE 
        nss = nss_empregado;
END $$

DROP PROCEDURE IF EXISTS pr_DatosProxectos $$
CREATE PROCEDURE pr_DatosProxectos (
    IN num_proxecto_buscar INT,
    OUT nome_proxecto_salida VARCHAR(25),
    OUT lugar_salida VARCHAR(25),
    OUT num_departamento_salida INT
) 
BEGIN
    SELECT nome_proxecto, lugar, num_departamento 
    INTO nome_proxecto_salida, lugar_salida, num_departamento_salida
    FROM proxecto 
    WHERE num_proxecto = num_proxecto_buscar;
END $$

DROP PROCEDURE IF EXISTS pr_DepartControlaProxec $$
CREATE PROCEDURE pr_DepartControlaProxec (
    IN numProxectos INT
)
BEGIN
    -- Seleccionamos los departamentos que cumplen con el número mínimo de proyectos
    SELECT d.num_departamento, d.nome_departamento, d.nss_dirige, d.data_direccion
    FROM departamento d
    JOIN proxecto p ON d.num_departamento = p.num_departamento
    GROUP BY d.num_departamento, d.nome_departamento, d.nss_dirige, d.data_direccion
    HAVING COUNT(p.num_proxecto) >= numProxectos;
END $$

DROP FUNCTION IF EXISTS fn_nEmpDepart $$
CREATE FUNCTION fn_nEmpDepart(nome_departamento VARCHAR(25))
RETURNS INT
DETERMINISTIC
BEGIN
    DECLARE num_empregados INT;

    -- Contamos los empleados del departamento dado
    SELECT COUNT(*)
    INTO num_empregados
    FROM empregado e
    JOIN departamento d ON e.num_departamento = d.num_departamento
    WHERE d.nome_departamento = nome_departamento;

    RETURN num_empregados;
END $$

DELIMITER ;

SELECT e.nss, CONCAT(e.nome, ' ', e.apelido_1, ' ', e.apelido_2) AS nome_completo, e.localidade, e.salario
            FROM empregado e
            JOIN empregado_proxecto pe ON e.nss = pe.nss_empregado
            GROUP BY e.nss, e.nome, e.apelido_1, e.apelido_2, e.localidade, e.salario
            HAVING COUNT(pe.num_proxecto) > 2;

/* SET FOREIGN_KEY_CHECKS=0; */
/* SET FOREIGN_KEY_CHECKS=1; */

INSERT INTO DEPARTAMENTO VALUES (1, 'PERSOAL', '1111111', '2000-03-12');
INSERT INTO DEPARTAMENTO VALUES (2, 'CONTABILIDADE', '2525252', '2001-02-22');
INSERT INTO DEPARTAMENTO VALUES (3, 'TÉCNICO', '2221111', '2002-11-13');
INSERT INTO DEPARTAMENTO VALUES (4, 'INFORMÁTICA', '8888889', '2001-08-19');
INSERT INTO DEPARTAMENTO VALUES (5, 'ESTADÍSTICA', '4444444', '2003-05-14');
INSERT INTO DEPARTAMENTO VALUES (6, 'INNOVACIÓN', '7777777', '2004-06-16');
INSERT INTO EMPREGADO VALUES ('Rocio','López', 'Ferreiro', '0010010', 'Montero Ríos', 145, '6-
G','36208','Vigo', '1999-3-12', 1300, 'M', '1010001', 1);
INSERT INTO EMPREGADO VALUES ('Javier','Quintero', 'Alvarez', '0110010', 'Montevideo', 10, '2-F', '36209',
'Vigo','1987-5-12', 1456.99, 'H','1010001', 1);
INSERT INTO EMPREGADO VALUES ('Germán', 'Gómez', 'Rodríguez','0999900','Sanjurjo Badía', 98, '3 -D',
'36212','Vigo','1997-5-13', 8500.40, 'H', '8888889', 4);
INSERT INTO EMPREGADO VALUES ('Diego', 'Lamela', 'Bello', '1010001', 'Camelias', 123, '4-A', '36211', 'Vigo',
'1989-4-23', 2000, 'H', '1111111', 1);
INSERT INTO EMPREGADO VALUES ('Felix', 'Barreiro', 'Casa', '1100222', 'Rinlo', 5, ' 5-C', '27709', 'Ribadeo',
'1987-6-22', 5000, 'H', '7777777', 6);
INSERT INTO EMPREGADO VALUES ('Pepe', 'López', 'López', '1111111', 'Olmo', 23, '4-B', '27003', 'Lugo', '1997-5-11', 3000, 'H', NULL, 1);
INSERT INTO EMPREGADO VALUES ('Xiao', 'Vecino', 'Vecino', '1122331', 'Brasil', 10, '2', NULL, 'Vigo', '1998-4-26', 2390, 'H', '2525252', 2);
INSERT INTO EMPREGADO VALUES ('Eligio', 'Rodrigo', 'Olmo', '1231231', 'Espiño', 3, '8', '15708', 'Santi ago',
'1987-12-9', 2197, 'H', '4444444', 5);
INSERT INTO EMPREGADO VALUES ('Marta', 'Bello', 'Arias', '1341431', 'Gran Vía', 23, '4-D', NULL, 'Vigo',
'1997-6-12', 1500, 'M', '2525252', 2);
INSERT INTO EMPREGADO VALUES ('Duarte', 'Xil', 'Torres', '2221111', 'Sol', 44, '1 -A', '27002', 'Lugo', '1987-11-14', 1500, 'H', '1111111', 3);
INSERT INTO EMPREGADO VALUES ('José Manuel', 'García', 'Graña', '2525252', 'Illas Canarias', 101, '2-B', NULL,
'Vigo', '2000-9-1', 3110, 'H', '1111111', 2);
INSERT INTO EMPREGADO VALUES ('Javier', 'Lamela', 'López', '3330000', 'Avda de Vigo', 120, '4-C', NULL,
'Pontevedra', '1977-7-2', 2650, 'H', '2221111', 3);
INSERT INTO EMPREGADO VALUES ('Paula', 'Mar', 'López', '3338883', 'Piñeira', 10, NULL, '27400', 'Monfo rte',
'1967-5-11', 2250, 'M', '6000006', 3);
INSERT INTO EMPREGADO VALUES ('Rosa', 'Murillo', 'Rivera', '4044443', 'Piñeira', 25, NULL, '27400',
'Monforte', '1987-5-11', 2150, 'M','6000006', 3);
INSERT INTO EMPREGADO VALUES ('Sara', 'Plaza', 'Marín', '4444444', 'Ciruela', 10, '6 -B', '15705', 'Santiago',
'1987-8-12', 2950, 'M', '1111111', 5);
INSERT INTO EMPREGADO VALUES ('Antonia', 'Romero', 'Boo', '4444999', 'Olmedo', 10, NULL, NULL, 'Santiago',
'1988-6-2', 2850, 'M', '8888889', 4);
INSERT INTO EMPREGADO VALUES ('Uxío', 'Cabado', 'Penalta', '5000000', 'Nueva', 20, '3 -C', NULL, 'Santiago',
'1987-8-14', 2600, 'H', '2221111', 3);
INSERT INTO EMPREGADO VALUES ('Anxos', 'Loures', 'Freire', '5555000', 'Rosalía de Castro', 105, '4-F', NULL,
'Santiago', '1978-8-12', 20500.0000, 'M', '5000000', 3);
INSERT INTO EMPREGADO VALUES ('Beatríz', 'Mallo', 'López', '6000006', 'Cardenal Quiroga', 10, '2-A', '27400',
'Monforte', '1976-5-9', 26000.0000, 'M', '2221111', 3);
INSERT INTO EMPREGADO VALUES ('Carmen', 'Jurado', 'Vega', '6000600', 'Oliva', 10, '2', NULL, 'Pontev edra',
'1983-7-15',1500, 'M', '3330000', 3);
INSERT INTO EMPREGADO VALUES ('Valeriano', 'Boo', 'Boo', '6565656', 'Marina', 23, '2', NULL, 'Ribadeo','1987-6-16', 25000, 'H', '1100222', 6);
INSERT INTO EMPREGADO VALUES ('Alex', 'Méndez', 'García', '7000007', 'Peregrina', 3, '1', NULL , 'Pontevedra',
'1986-5-11', 2300, 'H', '3330000', 3);
INSERT INTO EMPREGADO VALUES ('Ana María', 'Ramilo', 'Barreiro', '7777777', 'Virxe da cerca', 23, NULL, NULL,
'Santiago', '1986-4-8', 3100, 'M', '1111111', 6);
INSERT INTO EMPREGADO VALUES ('Rubén', 'Guerra', 'Vázquez', '8888877', 'Preguntoiro', 11, '1', NULL,
'Santiago', '1986-6-6', 2500, 'H', '7777777', 6);
INSERT INTO EMPREGADO VALUES ('Agostiño', 'Cerviño', 'Seoane', '8888889', 'Montero Ríos', 120, '4 -D', '36208',
'Vigo','1986-7-14', 3250, 'H', '1111111', 4);
INSERT INTO EMPREGADO VALUES ('Angeles', 'López', 'Arias', '9876567', 'San Telmo', 5, '2-C', '36680', 'A
Estrada', '1987-7-13', 1560, 'M', '4444444', 5);
INSERT INTO EMPREGADO VALUES ('Breixo', 'Pereiro', 'Lamela', '9900000', 'Sar', 29, '1' , NULL, 'Santiago',
'1987-2-3', 1400, 'H', '4444999', 4);
INSERT INTO EMPREGADO VALUES ('Celia', 'Bueno', 'Valiña', '9990009', 'Montero Ríos', 120, '4-D', '36208',
'Vigo', '1985-7-1', 1800, 'M', '1010001', 1);
INSERT INTO EMPREGADO VALUES ('Paulo', 'Máximo', 'Guerra', '9998888', 'Montero Ríos', 29, '2-A', NULL,'Santiago', '1984-5-8', 1500, 'H', '7777777', 6);
INSERT INTO PROXECTO VALUES (1,'XESTION DE PERSOAL', 'VIGO', 4);
INSERT INTO PROXECTO VALUES (2,'PORTAL', 'SANTIAGO', 4);
INSERT INTO PROXECTO VALUES (3,'APLICACIÓN CONTABLE', 'VIGO', 4);
INSERT INTO PROXECTO VALUES (4,'INFORME ESTADISTICO ANUAL', 'A ESTRADA', 5);
INSERT INTO PROXECTO VALUES (5,'PRODUCIÓN NOVO PRODUTO', 'RIBADEO', 6);
INSERT INTO PROXECTO VALUES (6,'DESEÑO NOVO CPD LUGO', 'LUGO', 3);
INSERT INTO PROXECTO VALUES (7,'MELLORAS SOCIAIS', 'VIGO', 1);
INSERT INTO PROXECTO VALUES (8,'DESEÑO NOVA TENDA VIGO', 'MONFORTE', 3);
INSERT INTO PROXECTO VALUES (9,'PROXECTO X', 'SANTIAGO', 5);
INSERT INTO PROXECTO VALUES (10,'PROXECTO Y', 'PONTEVEDRA', 3);
INSERT INTO EMPREGADO_PROXECTO VALUES ('0010010', 8, 20);
INSERT INTO EMPREGADO_PROXECTO VALUES ('0110010', 7, 20);
INSERT INTO EMPREGADO_PROXECTO VALUES ('0999900', 1, 30);
INSERT INTO EMPREGADO_PROXECTO VALUES ('0999900', 3, 20);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1010001', 1, 25);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1010001', 7, 15);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1100222', 5, 30);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1122331', 8, 35);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1231231', 4, 20);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1231231', 9, 20);
INSERT INTO EMPREGADO_PROXECTO VALUES ('1341431', 3, 15);
INSERT INTO EMPREGADO_PROXECTO VALUES ('2221111', 6, 20);
INSERT INTO EMPREGADO_PROXECTO VALUES ('2221111', 8, 10);
INSERT INTO EMPREGADO_PROXECTO VALUES ('3330000', 10, 25);
INSERT INTO EMPREGADO_PROXECTO VALUES ('3338883', 8, 30);
INSERT INTO EMPREGADO_PROXECTO VALUES ('4044443', 8, 15);
INSERT INTO EMPREGADO_PROXECTO VALUES ('4444999', 2, 30);
INSERT INTO EMPREGADO_PROXECTO VALUES ('6000006', 8, 25);
INSERT INTO EMPREGADO_PROXECTO VALUES ('8888889', 1, 30);
INSERT INTO EMPREGADO_PROXECTO VALUES ('8888889', 2, 10);
INSERT INTO EMPREGADO_PROXECTO VALUES ('8888889', 7, 5);
INSERT INTO EMPREGADO_PROXECTO VALUES ('9876567', 4, 35);
INSERT INTO EMPREGADO_PROXECTO VALUES ('9876567', 9, 10);
INSERT INTO EMPREGADO_PROXECTO VALUES ('9900000', 2, 40);
INSERT INTO EMPREGADO_PROXECTO VALUES ('9990009', 7, 20);