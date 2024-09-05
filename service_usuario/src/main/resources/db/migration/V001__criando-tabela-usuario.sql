CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(50),
    password VARCHAR(200),
    data_criacao DATE,
    data_atualizacao DATE,
    eh_admin BOOLEAN
);