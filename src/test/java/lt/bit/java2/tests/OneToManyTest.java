package lt.bit.java2.tests;

import lt.bit.java2.model.Client;
import lt.bit.java2.model.Invoice;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OneToManyTest extends BaseTest {

    @Test
    public void readClient() {
        EntityManager em = DBServiceTest.getEntityManager();

        Client client = em.find(Client.class, 1);
        Assert.assertEquals("Microsoft", client.getName());
        Assert.assertEquals(2, client.getInvoices().size());
        Assert.assertEquals(BigDecimal.valueOf(200.99), client.getInvoices().get(0).getSum());
        Assert.assertEquals(BigDecimal.valueOf(1000.99), client.getInvoices().get(1).getSum());

        em.close();
    }

    @Test
    public void readInvoice() {
        EntityManager em = DBServiceTest.getEntityManager();

        Invoice invoice = em.find(Invoice.class, 2);
        Assert.assertEquals(BigDecimal.valueOf(1000.99), invoice.getSum());
        Assert.assertEquals("Microsoft", invoice.getClient().getName());

        em.close();
    }

    @Test
    public void createInvoice() {
        EntityManager em = DBServiceTest.getEntityManager();

        // Kuriame is Invoice puses
        em.getTransaction().begin();

        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setSum(BigDecimal.valueOf(999.99));

        // jei tikrai zinome kad clientas su tokiu id egzistuoja
        // tai vietoje 'find' galima naudoti 'getReference' metoda,
        // nes mums reikalingas tik to kliento id - sutaupysime viena SQL SELECT

        // Client client = em.find(Client.class, 1);
        Client client = em.getReference(Client.class, 1);
        invoice.setClient(client);

        em.persist(invoice);

        em.getTransaction().commit();
        em.close();

        // patikriname ar naujas invoice turi sugeneruota id
        Assert.assertNotNull(invoice.getId());

        System.out.println("Antra dalis");

        // Patikriname ar tikrai klientas su id = 1 (Microsoft)
        // dabar turi jau 3 invoisus

        em = DBServiceTest.getEntityManager();

        Client c1 = em.find(Client.class, 1);
        Assert.assertEquals(3, c1.getInvoices().size());

        em.close();
    }

    /**
     * bandome sukurti invoice su neegzistuojanciu kliento id
     * - turime gauti klaida
     */
    @Test
    public void createInvoiceWithNonExistingClient() {
        EntityManager em = DBServiceTest.getEntityManager();

        // 1. Kuriame is Invoice puses
        em.getTransaction().begin();

        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setSum(BigDecimal.valueOf(999.99));

        Client client = em.getReference(Client.class, 100);
        invoice.setClient(client);

        try {
            em.persist(invoice);

            em.getTransaction().commit();

            Assert.fail("Turejo buti klaida!");

        } catch (Exception e) {
            em.getTransaction().rollback();
        }

        em.close();
    }

}

/*

@Entity
class A

    @Id
    private int id;

    @Column
    private int abc;

    ...

    public int hibernateProxyGetAbc() {
        // ....
        return getAbc();
    }

    public int getAbc() {
        return abc;
    }

    ...

 */
