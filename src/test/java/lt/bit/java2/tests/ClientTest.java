package lt.bit.java2.tests;

import lt.bit.java2.model.Client;
import lt.bit.java2.model.Invoice;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class ClientTest extends BaseTest {

    @Test
    public void client() {
        DBService dbService = new DBService();

        EntityManager em = dbServiceTest.getEntityManager();
        Assert.assertNotNull(em);

        Client client = em.find(Client.class, 1);
        Assert.assertNotNull(client);
        Assert.assertEquals("Microsoft", client.getName());

        dbServiceTest.close();
    }

    @Test
    public void invoice() {
        DBService dbService = new DBService();

        EntityManager em = dbService.getEntityManager();
        Invoice invoice = em.find(Invoice.class, 1);
        Assert.assertEquals(Integer.valueOf(1), invoice.getClientId());
        Assert.assertEquals(LocalDate.of(2020, 1, 1), invoice.getDate());

        dbServiceTest.close();
    }

    /**
     * Kai kazka keiciame duomenu bazeje, butina ta daryti tranzakcijoje
     */
    @Test
    public void deleteInvoice() {
        DBService dbService = new DBService();

        EntityManager em = dbService.getEntityManager();
        em.getTransaction().begin();
        Invoice invoice = em.find(Invoice.class, 1);
        em.remove(invoice);
        em.getTransaction().commit();
        em.close();

        em = dbService.getEntityManager();
        invoice = em.find(Invoice.class, 1);
        Assert.assertNull(invoice);
        em.close();

        dbService.close();
    }

    @Test
    public void insertClient() {
        DBService dbService = new DBService();

        // 1. gaunam EntityManager ir pradedam tranzakcija
        EntityManager em = dbService.getEntityManager();
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
        em = dbService.getEntityManager();
        Client c2 = em.find(Client.class, client.getId());
        em.close();

        Assert.assertEquals("AB Ragai ir Kanopos", c2.getName());

        dbService.close();
    }
}
