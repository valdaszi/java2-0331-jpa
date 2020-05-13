package lt.bit.java2.services;

import lt.bit.java2.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

public class DBService {

    static Logger LOG = Logger.getLogger(DBService.class.getName());

    static private EntityManagerFactory emf;
    static {
        LOG.info("Building EntityManagerFactory");
        // sukurti EntityManagerFactory naudojant konfiguracini faila:
        // persistence.xml
        // ir jis turi buti kataloge META-INF
        emf = Persistence.createEntityManagerFactory("pu.parduotuve");
    }

    static public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
