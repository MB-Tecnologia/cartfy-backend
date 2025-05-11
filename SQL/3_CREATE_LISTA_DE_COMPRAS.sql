CREATE TABLE lista_de_compras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);


CREATE TABLE produto_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    preco DECIMAL(10,2),
    url_img VARCHAR(500),
    gtin VARCHAR(50),
    quantidade INT,
    lista_id INT,
    FOREIGN KEY (lista_id) REFERENCES lista_de_compras(id) ON DELETE CASCADE
);