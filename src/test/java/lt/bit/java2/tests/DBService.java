package lt.bit.java2.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.function.Function;

public class DBService {

    private EntityManagerFactory emf;
    {
        // sukurti EntityManagerFactory naudojant konfiguracini faila:
        // persistence.xml
        // ir jis turi buti kataloge META-INF
        emf = Persistence.createEntityManagerFactory("pu.parduotuve");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void close() {
        emf.close();
    }

    public Object executeInTransaction(Function<EntityManager, Object> f) throws SQLException {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            // Vykdome musu koda
            Object obj = f.apply(em);

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
