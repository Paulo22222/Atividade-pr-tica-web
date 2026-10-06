-- =========================================================
-- DDL - Sistema de Vendas (Módulo Base: Marca, Categoria, Produto)
-- Disciplina: Programação WEB 2 - Atividade Prática CRUD
-- Aluno: Paulo Renato Mendes Silva
--
-- Execute este script dentro do banco "postgres" (ou crie um
-- banco próprio antes, veja o comentário abaixo).
-- =========================================================

-- Se preferir um banco dedicado em vez de usar o "postgres" padrão,
-- rode a linha abaixo separadamente (fora de uma transação) e depois
-- conecte nesse banco antes de rodar o restante do script:
-- CREATE DATABASE vendas_db;

DROP TABLE IF EXISTS tb_produto CASCADE;
DROP TABLE IF EXISTS tb_categoria CASCADE;
DROP TABLE IF EXISTS tb_marca CASCADE;

-- =========================
-- Marca
-- =========================
CREATE TABLE tb_marca (
    id          SERIAL PRIMARY KEY,
    nome        VARCHAR(150) NOT NULL UNIQUE,
    descricao   VARCHAR(500),
    ativo       BOOLEAN NOT NULL DEFAULT TRUE
);

-- =========================
-- Categoria (árvore recursiva)
-- =========================
CREATE TABLE tb_categoria (
    id            SERIAL PRIMARY KEY,
    nome          VARCHAR(150) NOT NULL,
    categoria_id  INTEGER NULL REFERENCES tb_categoria(id)
);

-- =========================
-- Produto
-- =========================
CREATE TABLE tb_produto (
    id                   SERIAL PRIMARY KEY,
    nome                 VARCHAR(200) NOT NULL,
    descricao            TEXT,
    preco                NUMERIC(10,2) NOT NULL CHECK (preco > 0),
    quantidade_estoque   INTEGER NOT NULL DEFAULT 0 CHECK (quantidade_estoque >= 0),
    marca_id             INTEGER NOT NULL REFERENCES tb_marca(id),
    categoria_id         INTEGER NOT NULL REFERENCES tb_categoria(id),
    caminho_imagem       VARCHAR(255)
);

CREATE INDEX idx_produto_marca ON tb_produto(marca_id);
CREATE INDEX idx_produto_categoria ON tb_produto(categoria_id);
CREATE INDEX idx_categoria_pai ON tb_categoria(categoria_id);
