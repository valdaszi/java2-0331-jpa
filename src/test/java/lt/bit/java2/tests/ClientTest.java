package lt.bit.java2.tests;

import lt.bit.java2.model.Client;
import lt.bit.java2.model.Invoice;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClientTest extends BaseTest {

    @Test
    public void readClient() {
        EntityManager em = dbServiceTest.getEntityManager();
        Assert.assertNotNull(em);

        Client client = em.find(Client.class, 1);
        Assert.assertNotNull(client);
        Assert.assertEquals("Microsoft", client.getName());

        dbServiceTest.close();
    }


    /**
     * Kai kazka keiciame duomenu bazeje, butina ta daryti tranzakcijoje
     */

    @Test
    public void insertClient() {
        // 1. gaunam EntityManager ir pradedam tranzakcija
        EntityManager em = dbServiceTest.getEntityManager();
        em.getTransaction().begin();

        // 2. Atliekam reikiamus veiksmus
        Client client = new Client();
        client.setName("AB Ragai ir Kanopos");

        Assert.assertNull(client.getId());

        em.persist(client);

        // 3. Uzdarome tranzakcija ir entityManager
        em.getTransaction().commit();
        em.close();

        // Patkrinam ar naujai sukurtas klientas turi sugeneruota duomenu bazeje nauja id
        Assert.assertNotNull(client.getId());

        // Bandime nuskaityti klienta su nauju id ir patikrinti ar vardas sutampa
        em = dbServiceTest.getEntityManager();
        Client c2 = em.find(Client.class, client.getId());
        em.close();

        Assert.assertEquals("AB Ragai ir Kanopos", c2.getName());

        dbServiceTest.close();
    }

    @Test
    public void deleteClient() {
        // 1. gaunam EntityManager ir pradedam tranzakcija
        EntityManager em = dbServiceTest.getEntityManager();
        em.getTransaction().begin();

        try {

            // 2 Delete
            // 2.1. Pradzioje nuskaitome klienta id = 4 (Oracle)
            Client client = em.find(Client.class, 4);
            Assert.assertNotNull(client);

            // 2.2. Jei nuskaiteme sekmingai tai tada delete
            em.remove(client);

            // 3. Uzdarom tranzakcija
            em.getTransaction().commit();

        } catch (Throwable e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);

        } finally {
            em.close();
        }

        // Patikriname ar is tikruju istryneme
        em = dbServiceTest.getEntityManager();
        Client client = em.find(Client.class, 4);
        Assert.assertNull(client);
        em.close();

        dbServiceTest.close();
    }


    @Test
    public void updateClient() throws SQLException {
        dbServiceTest.executeInTransaction(em -> {
            // 1. nuskaityti norima pakeisti entity
            Client client = em.find(Client.class, 2);

            // 2. padarome pakeitumus
            client.setName("Maxima");

            // 3. Saugojame
            em.persist(client);

            return null;
        });

        EntityManager em = dbServiceTest.getEntityManager();
        Client client = em.find(Client.class, 2);
        Assert.assertEquals("Maxima", client.getName());

        dbServiceTest.close();
    }

    @Test
    public void mergeClient() throws SQLException {
        EntityManager em = dbServiceTest.getEntityManager();
        Client client = em.find(Client.class, 2);
        em.close();

        Assert.assertEquals("IBM", client.getName());
        client.setName("Rimi");

        dbServiceTest.executeInTransaction(entityManager -> {
            // entityManager.persist(client);
            entityManager.merge(client);
            return client;
        });

        em = dbServiceTest.getEntityManager();
        Client client2 = em.find(Client.class, 2);
        Assert.assertEquals("Rimi", client2.getName());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetReference() {
        EntityManager em = dbServiceTest.getEntityManager();

        Client client = em.getReference(Client.class, 1);
        Assert.assertNotNull(client);
        Assert.assertEquals(Integer.valueOf(1), client.getId());
        Assert.assertEquals("Microsoft", client.getName());

        // 1. galima gaudyti exception'a ir rodyti klaida jei toks exception'as nera metamas
//        try {
//            client = em.getReference(Client.class, 100);
//            Assert.assertNotNull(client);
//            Assert.assertEquals(Integer.valueOf(100), client.getId());
//            client.getName();
//            Assert.fail("EntityNotFoundException not thrown");
//        } catch (EntityNotFoundException ignore) {}

        // 2. tokiu atveju reikia testini metoda anotuoti kad jis privalo mesti EntityNotFoundException
        client = em.getReference(Client.class, 100);
        Assert.assertNotNull(client);
        Assert.assertEquals(Integer.valueOf(100), client.getId());
        client.getName();

        dbServiceTest.close();
    }

    @Test
    public void managedEntitiesTest() throws SQLException {
        // jei nieko nekeiciame tai niekas neturetu buti saugojama
        // t.y. neturetu buti generuojami SQL UPDATE
        dbServiceTest.executeInTransaction(em -> {
            // 1. nuskaityti norima pakeisti entity
            Client client = em.find(Client.class, 1);

            // 2. padarome pakeitumus
            client.setName("Microsoft");

            // 3. Saugojame
            em.persist(client);

            return null;
        });


        // jei kazkoki lauka pakeiciame tai mums nebutina kviesti persist metoda

        EntityManager em = dbServiceTest.getEntityManager();
        em.getTransaction().begin();

        // 1. nuskaityti norima pakeisti entity
        Client client1 = em.find(Client.class, 1);
        Client client2 = em.find(Client.class, 2);

        Client client3 = new Client();
        client3.setName("Iki");
        Client client31 = em.merge(client3);

        // 2. padarome pakeitumus
        client1.setName("Maxima");
        client2.setName("Rimi");

        em.getTransaction().commit();
        em.close();

        // Jei objektas/entity client3 yra tik su merge'intas tai jis lieka nevaldomas entityManager'io
        // bet entityManager'yje atsiranda to entity valdoma kopija
        Assert.assertNull(client3.getId());
        Assert.assertNotNull(client31.getId());

        dbServiceTest.close();
    }
}
