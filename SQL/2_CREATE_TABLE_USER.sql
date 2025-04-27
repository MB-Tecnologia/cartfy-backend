CREATE TABLE usuario (
	id int NOT NULL AUTO_INCREMENT,
	cpf_cnpj varchar(14),
	nome varchar(50),
    email varchar(320),
    senha varchar(200),
    telefone int NULL,
    cep int,
    numero_endereco int NULL,
    dt_incluso datetime,
    
    PRIMARY KEY(id)
);
