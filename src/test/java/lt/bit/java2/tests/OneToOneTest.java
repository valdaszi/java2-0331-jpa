package lt.bit.java2.tests;

import lt.bit.java2.model.Client;
import lt.bit.java2.model.ClientEx;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class OneToOneTest extends BaseTest {

    @Test
    public void createClientEx() throws SQLException {
        DBServiceTest.executeInTransaction(entityManager -> {

            ClientEx clientEx = new ClientEx();
            clientEx.setPastaba("labai slapta zinute");
            clientEx.setTelefonas("123");

            Client client = entityManager.getReference(Client.class, 4);

            clientEx.setClient(client);

            entityManager.persist(clientEx);

            return null;
        });

        EntityManager em = DBServiceTest.getEntityManager();
        Client client = em.find(Client.class, 4);

        Assert.assertEquals("123", client.getClientEx().getTelefonas());

        em.close();
    }

    @Test
    public void deleteClientAll() throws SQLException {
        DBServiceTest.executeInTransaction(entityManager -> {

            ClientEx clientEx = new ClientEx();
            clientEx.setPastaba("labai slapta zinute");
            clientEx.setTelefonas("123");

            Client client = entityManager.getReference(Client.class, 4);

            clientEx.setClient(client);

            entityManager.persist(clientEx);

            return null;
        });

        EntityManager em = DBServiceTest.getEntityManager();
        ClientEx clientEx = em.find(ClientEx.class, 4);
        Assert.assertNotNull(clientEx);
        em.close();

        DBServiceTest.executeInTransaction(entityManager -> {

            Client client = entityManager.find(Client.class, 4);
            entityManager.remove(client);

            return null;
        });

        em = DBServiceTest.getEntityManager();
        clientEx = em.find(ClientEx.class, 4);
        Assert.assertNull(clientEx);
        em.close();
    }

}
