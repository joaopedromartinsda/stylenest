CREATE TABLE IF NOT EXISTS produtos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(1000),
    preco NUMERIC(10, 2) NOT NULL,
    imagem_url VARCHAR(512),
    categoria VARCHAR(255) NOT NULL
);
