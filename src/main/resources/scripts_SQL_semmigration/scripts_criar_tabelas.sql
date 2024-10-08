--criacao das tabelas

-- Tabela de Usuários
CREATE TABLE usuarios (
    cpf VARCHAR(11) PRIMARY KEY,  -- CPF como chave primária
    nome VARCHAR(100) NOT NULL,
    perfil VARCHAR(10) CHECK (perfil IN ('cliente', 'admin')) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

-- Tabela de Clientes
CREATE TABLE clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    data_de_cadastro DATE NOT NULL,
    FOREIGN KEY (cpf) REFERENCES usuarios(cpf) -- Referencia o CPF na tabela de usuários
);

-- Tabela de Endereços
CREATE TABLE enderecos (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER REFERENCES clientes(id) ON DELETE CASCADE,
    logradouro VARCHAR(255) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    complemento VARCHAR(255),
    tag VARCHAR(50)
);

-- Tabela de Contatos
CREATE TABLE contatos (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER REFERENCES clientes(id) ON DELETE CASCADE,
    tag VARCHAR(50),
    tipo VARCHAR(10) CHECK (tipo IN ('e-mail', 'telefone')) NOT NULL,
    valor VARCHAR(100) NOT NULL
);

-- Tabela de Raças
CREATE TABLE racas (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL
);

-- Tabela de Pets
CREATE TABLE pets (
    id SERIAL PRIMARY KEY,
    id_cliente INTEGER REFERENCES clientes(id) ON DELETE CASCADE,
    id_raca INTEGER REFERENCES racas(id),
    data_de_nascimento DATE NOT NULL,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE public.atendimento (
    id bigint NOT NULL,
    pet_id bigint NOT NULL,
    descricao_do_atendimento character varying(255) NOT NULL,
    valor numeric(38,2) NOT NULL,
    data date NOT NULL
);

