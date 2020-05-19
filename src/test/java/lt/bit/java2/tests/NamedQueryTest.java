package lt.bit.java2.tests;

import lt.bit.java2.model.Client;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class NamedQueryTest {

    @Test
    public void allClient() {
        EntityManager em = DBServiceTest.getEntityManager();

//        Query query = em.createNamedQuery(Client.QUERY_ALL);
//        List<?> result = query.getResultList();

        TypedQuery<Client> query = em.createNamedQuery(Client.QUERY_ALL, Client.class);
        List<Client> result = query.getResultList();

        Assert.assertEquals(6, result.size());
        Assert.assertEquals("Microsoft", result.get(0).getName());
        Assert.assertEquals(2, result.get(0).getInvoices().size());

        em.close();
        DBServiceTest.close();
    }

    /**
     * Galima Query sukonstruoti dinamiskai, t.y. programos kode
     */
    @Test
    public void allClientFromCode() {
        EntityManager em = DBServiceTest.getEntityManager();

        TypedQuery<Client> query = em.createQuery( "SELECT c FROM Client c", Client.class)
                .setFirstResult(1)
                .setMaxResults(2);
        List<Client> result = query.getResultList();

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("IBM", result.get(0).getName());
        Assert.assertEquals(1, result.get(0).getInvoices().size());

        em.close();
        DBServiceTest.close();
    }
}
