package lt.bit.java2.tests;

import lt.bit.java2.model.Invoice;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;

public class InvoiceTest extends BaseTest {

    @Test
    public void readInvoice() {
        EntityManager em = DBServiceTest.getEntityManager();
        Invoice invoice = em.find(Invoice.class, 1);
        Assert.assertEquals(LocalDate.of(2020, 1, 1), invoice.getDate());
    }


    @Test
    public void deleteInvoice() {
        EntityManager em = DBServiceTest.getEntityManager();
        em.getTransaction().begin();
        Invoice invoice = em.find(Invoice.class, 1);
        em.remove(invoice);
        em.getTransaction().commit();
        em.close();

        em = DBServiceTest.getEntityManager();
        invoice = em.find(Invoice.class, 1);
        Assert.assertNull(invoice);
        em.close();
    }

}
