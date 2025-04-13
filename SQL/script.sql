CREATE DATABASE CARTFY;

CREATE TABLE usuario (
	id int NOT NULL AUTO_INCREMENT,
	cpf_cnpj varchar(14),
	nome varchar(50),
    email varchar(320),
    senha varchar(200),
    dt_incluso datetime
);



CREATE TABLE lista_de_compras (
	id_lista bigint NOT NULL AUTO_INCREMENT,
    id_usuario int NOT NULL ,
	nome varchar(50),
    url_lista varchar(50),    
    dt_inclusao datetime,
    dt_alteracao datetime,
    
    PRIMARY KEY(id_lista),
    FOREIGN KEY(id_usuario) REFERENCES usuario(id)
);
