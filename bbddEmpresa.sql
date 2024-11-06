DROP DATABASE IF EXISTS BBDD_EMPRESA;
CREATE DATABASE IF NOT EXISTS BBDD_EMPRESA;
USE BBDD_EMPRESA;

DROP TABLE IF EXISTS empregado;
CREATE TABLE IF NOT EXISTS empregado (
    nome varchar(25) not null,
    apelido_1 varchar(25) not null,
    apelido_2 varchar(25) not null,
    nss varchar(15) not null,
    rua varchar(30) null,
    numero_rua int null,
    piso varchar(4) null,   
    cp char(5) null,
    localidade varchar(25),
    data_nacemento date null,
    salario float null,
    sexo char(1),
    nss_supervisa varchar(15) null,
    num_departamento_pertenece int null,
    
     PRIMARY KEY (nss)
) ENGINE INNODB;

DROP TABLE IF EXISTS departamento;
CREATE TABLE IF NOT EXISTS departamento (
    num_departamento int not null,
    nome_departamento varchar(25) not null,
    nss_dirige varchar(15) null,
    data_direccion date null,
    
	PRIMARY KEY (num_departamento)
) ENGINE INNODB;

DROP TABLE IF EXISTS proxecto;
CREATE TABLE IF NOT EXISTS proxecto (
    num_proxecto int not null,
    nome_proxecto varchar(25) not null,
    lugar varchar(25) not null,
    num_departamento int not null,
    
     PRIMARY KEY (num_proxecto)
) ENGINE INNODB;

DROP TABLE IF EXISTS empregado_proxecto;
CREATE TABLE IF NOT EXISTS empregado_proxecto (
    nss_empregado varchar(15) not null,
    num_proxecto int not null,
    horas_semanais int null
    
    PRIMARY KEY (num_proxecto, nss_empregado)
) ENGINE INNODB;