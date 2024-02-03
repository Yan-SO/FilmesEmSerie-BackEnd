tabelas do banco de dados MySQL:

CREATE TABLE Usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_usuario VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE Itens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_item VARCHAR(255) NOT NULL,
    nota FLOAT,
    status_item ENUM('INTERESSADO', 'VENDO', 'TERMINADO', 'LARGADO'),
    tipo ENUM('FILME', 'SERIE', 'DESENHO', 'ANIME'),
    id_usuario INT,
    imagem LONGBLOB,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);
