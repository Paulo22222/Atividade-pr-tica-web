package br.edu.vendas.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Fábrica central de EntityManager. A EntityManagerFactory é cara de criar,
 * então mantemos uma única instância para toda a aplicação (padrão comum em
 * projetos sem um servidor de aplicação full Java EE / sem JTA).
 */
public class JPAUtil {

    private static final String PERSISTENCE_UNIT = "vendas_pu";
    private static EntityManagerFactory emf;

    private JPAUtil() {
    }

    private static synchronized EntityManagerFactory getFactory() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }
        return emf;
    }

    /**
     * Cada chamador é responsável por fechar o EntityManager retornado
     * (use try/finally ou try-with-resources quando possível).
     */
    public static EntityManager getEntityManager() {
        return getFactory().createEntityManager();
    }
}
