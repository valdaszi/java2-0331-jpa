package lt.bit.java2.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBService {

    static private EntityManagerFactory emf;
    static {
        // sukurti EntityManagerFactory naudojant konfiguracini faila:
        // persistence.xml
        // ir jis turi buti kataloge META-INF
        emf = Persistence.createEntityManagerFactory("pu.parduotuve");
    }

    static public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
