package lt.bit.java2.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

}
