package lt.bit.java2.tests;

import javassist.NotFoundException;
import lt.bit.java2.model.Client;
import lt.bit.java2.model.ClientEx;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public class OneToOneTest extends BaseTest {

    /**
     * Kuriame ClientEx ir pridedame ji prie jau egzistuojancio Client
     * @throws SQLException jei klaida
     */
    @Test
    public void createClientEx() throws SQLException {
        // pradzioje isitikiname kad ClientEx nera
        EntityManager em = DBServiceTest.getEntityManager();
        ClientEx clEx = em.find(ClientEx.class, 4);
        Assert.assertNull(clEx);
        em.close();

        // sukuriam
        DBServiceTest.executeInTransaction(entityManager -> {

            ClientEx clientEx = new ClientEx();
            clientEx.setPastaba("labai slapta zinute");
            clientEx.setTelefonas("123");

            Client client = entityManager.getReference(Client.class, 4);

            clientEx.setClient(client);

            entityManager.persist(clientEx);

            return null;
        });

        // isitikiname kad ClientEx sukurtas
        em = DBServiceTest.getEntityManager();
        clEx = em.find(ClientEx.class, 4);
        Assert.assertNotNull(clEx);
        em.close();
    }

    /**
     * Kuriame vienu metu ir Client ir ClientEx
     * @throws SQLException jei klaida
     */
    @Test
    public void createClientAndClientEx() throws SQLException {
        Client clientNew = DBServiceTest.executeInTransaction(entityManager -> {

            Client client = new Client();
            client.setName("Iki");

            ClientEx clientEx = new ClientEx();
            clientEx.setPastaba("labai slapta zinute");
            clientEx.setTelefonas("123");
            clientEx.setClient(client);

            client.setClientEx(clientEx);

            entityManager.persist(client);

            return client;
        });
        Assert.assertNotNull(clientNew.getId());

        EntityManager em = DBServiceTest.getEntityManager();
        Client client = em.find(Client.class, clientNew.getId());

        Assert.assertEquals("Iki", client.getName());
        Assert.assertEquals("123", client.getClientEx().getTelefonas());

        em.close();
    }


    /**
     * Triname Client prie kurio buvo pridetas ClientEx
     * @throws SQLException jei klaida
     */
    @Test
    public void deleteClientAndClientEx() throws SQLException {
        // sukuriam ClientEx prie Client kurio id = 4
        createClientEx();

        // triname
        DBServiceTest.executeInTransaction(entityManager -> {

            Client client = entityManager.find(Client.class, 4);
            entityManager.remove(client);

            return null;
        });

        // patikriname ar tikrai istryneme
        EntityManager em = DBServiceTest.getEntityManager();
        ClientEx clientEx = em.find(ClientEx.class, 4);
        Assert.assertNull(clientEx);
        em.close();
    }

    /**
     * Triname tik ClientEx
     * @throws SQLException jei klaida
     */
    @Test
    public void deleteClientEx() throws SQLException {
        // sukuriam ClientEx prie Client kurio id = 4
        createClientEx();

        // triname ClientEx ji isvalydami Client'e
        DBServiceTest.executeInTransaction(entityManager -> {
            Client client = entityManager.find(Client.class, 4);
            Assert.assertNotNull(client.getClientEx());

            client.setClientEx(null);
            return null;
        });

        // patikriname ar tikrai istryneme
        EntityManager em = DBServiceTest.getEntityManager();
        Client client = em.find(Client.class, 4);
        Assert.assertNull(client.getClientEx());

        ClientEx clientEx = em.find(ClientEx.class, 4);
        Assert.assertNull(clientEx);
    }

    /**
     * Modifikuojame ir Client ir ClientEx
     * @throws SQLException jei klaida
     */
    @Test
    public void updateClientAndClientEx() throws SQLException {
        // sukuriam ClientEx prie Client kurio id = 4
        createClientEx();

        // modifikuojame ir Client ir ClientEx
        DBServiceTest.executeInTransaction(entityManager -> {
            Client client = entityManager.find(Client.class, 4);
            client.setName("Rimi");
            client.getClientEx().setTelefonas("000");
            return null;
        });

        // patiriname ar tikrai abu modifikavosi
        EntityManager em = DBServiceTest.getEntityManager();
        Client client = em.find(Client.class, 4);

        Assert.assertEquals("Rimi", client.getName());
        Assert.assertEquals("000", client.getClientEx().getTelefonas());
    }
}
