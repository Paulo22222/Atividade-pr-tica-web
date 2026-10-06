package br.edu.vendas.repository;

import br.edu.vendas.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

/**
 * Repositório genérico com as operações básicas de CRUD via JPA/Hibernate.
 * Cada método abre e fecha seu próprio EntityManager (estratégia simples,
 * adequada para uma aplicação sem contêiner JTA completo).
 */
public abstract class GenericRepository<E, ID extends Serializable> {

    protected abstract Class<E> getEntityClass();

    public void salvar(E entidade) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (getId(entidade) == null) {
                em.persist(entidade);
            } else {
                em.merge(entidade);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deletar(ID id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            E entidade = em.find(getEntityClass(), id);
            if (entidade != null) {
                em.remove(entidade);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public E buscarPorId(ID id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(getEntityClass(), id);
        } finally {
            em.close();
        }
    }

    public List<E> listar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "select e from " + getEntityClass().getSimpleName() + " e", getEntityClass())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Implementado nas subclasses para extrair o id da entidade (usado para
     * decidir entre persist/merge).
     */
    protected abstract ID getId(E entidade);
}
