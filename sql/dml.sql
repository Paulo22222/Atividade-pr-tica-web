-- =========================================================
-- DML - Dados de teste / carga inicial
-- Disciplina: Programação WEB 2 - Atividade Prática CRUD
-- Execute após o ddl.sql
-- =========================================================

-- Marcas
INSERT INTO tb_marca (nome, descricao, ativo) VALUES
('Samsung', 'Eletrônicos em geral', TRUE),
('Nike', 'Artigos esportivos', TRUE),
('Dell', 'Computadores e periféricos', TRUE),
('Genérica', 'Marca própria / sem fabricante definido', FALSE);

-- Categorias (raiz)
INSERT INTO tb_categoria (nome, categoria_id) VALUES
('Eletrônicos', NULL),
('Vestuário', NULL),
('Informática', NULL);

-- Subcategorias (nível 2)
INSERT INTO tb_categoria (nome, categoria_id) VALUES
('Celulares', (SELECT id FROM tb_categoria WHERE nome = 'Eletrônicos')),
('TVs', (SELECT id FROM tb_categoria WHERE nome = 'Eletrônicos')),
('Tênis', (SELECT id FROM tb_categoria WHERE nome = 'Vestuário')),
('Notebooks', (SELECT id FROM tb_categoria WHERE nome = 'Informática'));

-- Subcategoria (nível 3, para provar múltiplos níveis)
INSERT INTO tb_categoria (nome, categoria_id) VALUES
('Smartphones Android', (SELECT id FROM tb_categoria WHERE nome = 'Celulares'));

-- Produtos
INSERT INTO tb_produto (nome, descricao, preco, quantidade_estoque, marca_id, categoria_id, caminho_imagem) VALUES
('Galaxy S23', 'Smartphone Samsung linha S', 3999.90, 15,
    (SELECT id FROM tb_marca WHERE nome = 'Samsung'),
    (SELECT id FROM tb_categoria WHERE nome = 'Smartphones Android'),
    NULL),
('Smart TV 50"', 'TV 4K Samsung', 2599.00, 8,
    (SELECT id FROM tb_marca WHERE nome = 'Samsung'),
    (SELECT id FROM tb_categoria WHERE nome = 'TVs'),
    NULL),
('Tênis Air Max', 'Tênis esportivo Nike', 699.90, 30,
    (SELECT id FROM tb_marca WHERE nome = 'Nike'),
    (SELECT id FROM tb_categoria WHERE nome = 'Tênis'),
    NULL),
('Notebook Inspiron', 'Notebook Dell 15 polegadas', 4299.00, 5,
    (SELECT id FROM tb_marca WHERE nome = 'Dell'),
    (SELECT id FROM tb_categoria WHERE nome = 'Notebooks'),
    NULL);
