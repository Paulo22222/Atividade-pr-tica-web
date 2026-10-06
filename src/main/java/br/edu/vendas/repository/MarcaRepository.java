package br.edu.vendas.repository;

import br.edu.vendas.model.Marca;
import br.edu.vendas.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class MarcaRepository extends GenericRepository<Marca, Long> {

    @Override
    protected Class<Marca> getEntityClass() {
        return Marca.class;
    }

    @Override
    protected Long getId(Marca entidade) {
        return entidade.getId();
    }

    /**
     * Verifica se já existe outra marca com o mesmo nome (case-insensitive),
     * ignorando a própria marca quando estamos editando (idAtual pode ser null).
     */
    public boolean existeComMesmoNome(String nome, Long idAtual) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "select m.id from Marca m where lower(m.nome) = lower(:nome)"
                    + (idAtual != null ? " and m.id <> :idAtual" : "");
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("nome", nome);
            if (idAtual != null) {
                query.setParameter("idAtual", idAtual);
            }
            query.setMaxResults(1);
            try {
                query.getSingleResult();
                return true;
            } catch (NoResultException ex) {
                return false;
            }
        } finally {
            em.close();
        }
    }
}
