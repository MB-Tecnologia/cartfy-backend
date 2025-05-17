CREATE TABLE mercados (
	id_mercado bigint NOT NULL AUTO_INCREMENT,
    nome varchar(50) NOT NULL ,
	razaoSocial varchar(50) NULL,
    endereco varchar(50) NULL,    
    cnpj char(14)
    
    PRIMARY KEY(id_mercado)    
);


