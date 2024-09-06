CREATE TABLE email (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    email VARCHAR(150) NOT NULL,
    data_criacao DATE NOT NULL,
);