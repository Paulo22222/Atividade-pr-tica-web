package br.edu.vendas.repository;

import br.edu.vendas.model.Categoria;
import br.edu.vendas.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CategoriaRepository extends GenericRepository<Categoria, Long> {

    @Override
    protected Class<Categoria> getEntityClass() {
        return Categoria.class;
    }

    @Override
    protected Long getId(Categoria entidade) {
        return entidade.getId();
    }

    /**
     * Conta quantos produtos estão associados a esta categoria (usado para
     * impedir/alertar antes de excluir).
     */
    public long contarProdutosVinculados(Long categoriaId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "select count(p) from Produto p where p.categoria.id = :categoriaId", Long.class);
            query.setParameter("categoriaId", categoriaId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Retorna todas as categorias ordenadas de forma que dá para montar a
     * árvore facilmente (raízes primeiro, depois filhos).
     */
    public List<Categoria> listarOrdenadoPorNome() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select c from Categoria c order by c.nome", Categoria.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
