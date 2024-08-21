CREATE TABLE usuarios (
    cpf VARCHAR(11) PRIMARY KEY,  -- CPF como chave primária
    nome VARCHAR(100) NOT NULL,
    perfil VARCHAR(10) CHECK (perfil IN ('cliente', 'admin')) NOT NULL,
    senha VARCHAR(100) NOT NULL
);