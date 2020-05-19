package lt.bit.java2.services;

import lt.bit.java2.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.logging.Logger;

public class DBService {

    static private final Logger LOG = Logger.getLogger(DBService.class.getName());

    static protected EntityManagerFactory emf;

    static private EntityManagerFactory getEMF() {
        if (emf == null) {
            LOG.info("Building EntityManagerFactory");
            // sukurti EntityManagerFactory naudojant konfiguracini faila:
            // persistence.xml
            // ir jis turi buti kataloge META-INF
            emf = Persistence.createEntityManagerFactory("pu.parduotuve");
        }
        return emf;
    }

    static public EntityManager getEntityManager() {
        return  getEMF().createEntityManager();
    }

    static public <T> T executeInTransaction(Function<EntityManager, T> f) throws SQLException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            // Vykdome musu koda
            T obj = f.apply(em);

            em.getTransaction().commit();
            return obj;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new SQLException(e);

        } finally {
            em.close();
        }
    }
}
