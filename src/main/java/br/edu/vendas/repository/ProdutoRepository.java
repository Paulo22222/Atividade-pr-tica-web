package br.edu.vendas.repository;

import br.edu.vendas.model.Produto;

public class ProdutoRepository extends GenericRepository<Produto, Long> {

    @Override
    protected Class<Produto> getEntityClass() {
        return Produto.class;
    }

    @Override
    protected Long getId(Produto entidade) {
        return entidade.getId();
    }
}
