# Sistema de Vendas — Módulo Base (CRUD)
Disciplina: Programação WEB 2 — Atividade Prática
Professor: Danilo Souza Almeida

## Integrante
- Paulo Renato Mendes Silva

## Descrição da solução
Aplicação web Jakarta EE (JSF + PrimeFaces 15 + Hibernate/JPA + PostgreSQL)
que implementa o módulo inicial de catálogo de um sistema de vendas, com
CRUD completo de:

- **Marca** — nome único, descrição e status ativo/inativo.
- **Categoria** — árvore hierárquica recursiva (categoria pai/subcategorias),
  com validação contra ciclos e bloqueio de exclusão quando há subcategorias
  ou produtos vinculados.
- **Produto** — associado a Marca e Categoria, com upload e exibição de
  imagem (`<p:fileUpload>` / `<p:graphicImage>`).

### Arquitetura em camadas
```
br.edu.vendas.model        -> entidades JPA + Bean Validation
br.edu.vendas.repository   -> acesso a dados via EntityManager (JPA/Hibernate)
br.edu.vendas.service      -> regras de negócio e validações
br.edu.vendas.controller   -> Managed Beans CDI (@Named, @ViewScoped)
br.edu.vendas.converter    -> @FacesConverter para Marca e Categoria
br.edu.vendas.servlet      -> servlet que serve as imagens de produto
br.edu.vendas.util         -> utilitários (mensagens JSF, EntityManagerFactory)
```

## Tecnologias
Jakarta Faces (JSF) · PrimeFaces 15 · Hibernate/JPA 6 · Bean Validation ·
PostgreSQL · Apache Maven · CDI (Weld) · Lombok

## Pré-requisitos
- JDK 17
- Apache Maven
- PostgreSQL (testado com usuário `postgres`, porta `5432`)
- Servidor de aplicação Tomcat (configurado via NetBeans, conforme
  `nb-configuration.xml`)

## Como executar

### 1. Banco de dados
1. Abra o pgAdmin4 e conecte no servidor PostgreSQL local.
2. Execute o script `sql/ddl.sql` (cria as tabelas `tb_marca`, `tb_categoria`
   e `tb_produto`).
3. Execute o script `sql/dml.sql` (carga de dados de teste).

Por padrão a aplicação se conecta no banco `postgres` (usuário `postgres`,
senha `iftm`, porta `5432`) — veja/ajuste em
`src/main/resources/META-INF/persistence.xml` se seu ambiente for diferente.

### 2. Aplicação
1. Abra o projeto no NetBeans (projeto Maven).
2. Faça o build: `mvn clean package`.
3. Rode no Tomcat configurado no NetBeans, ou faça deploy manual do `.war`
   gerado em `target/`.
4. Acesse `http://localhost:8080/ppw2-java-web-maven/`.

### 3. Upload de imagens
As imagens enviadas no cadastro de Produto são salvas fora da pasta do WAR,
em `~/uploads-vendas/produtos/` (diretório do usuário do sistema operacional
que roda o Tomcat), para não serem perdidas a cada novo deploy. Elas são
servidas pela aplicação através de `/imagens/produtos/{arquivo}`.

## Capturas de tela
> Adicione aqui prints das telas de Marca, Categoria e Produto em
> funcionamento (listagem, cadastro e exclusão com confirmação) antes da
> entrega final.

## Scripts SQL
- `sql/ddl.sql` — criação das tabelas.
- `sql/dml.sql` — dados de teste (marcas, categorias em múltiplos níveis e
  produtos).

## Observações de entrega
- Histórico de commits atômicos e progressivos (ver seção de commits abaixo).
- `.gitignore` configurado para `target/`, arquivos de IDE, etc.
