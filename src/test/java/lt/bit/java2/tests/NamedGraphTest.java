package lt.bit.java2.tests;

import lt.bit.java2.model.Client;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

public class NamedGraphTest {


    /**
     * Pradzioje bandome gauti klienta su nurodytu invoices LAZY fetch
     * Turetume matyti sugeneruotus bent 2 select'us
     */
    @Test
    public void getClient() {
        EntityManager em = DBServiceTest.getEntityManager();

        Client client = em.find(Client.class, 1);

        Assert.assertEquals(2, client.getInvoices().size());

        em.close();
        DBServiceTest.close();
    }

    /**
     * Kaip istraukti duomenis apie clienta nekreipiant demesio koks
     * fetch tipas nurodytas
     */
    @Test
    public void getClientAllInfo() {
        EntityManager em = DBServiceTest.getEntityManager();

        EntityGraph graph = em.getEntityGraph(Client.GRAPH_INVOICES);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);

        Client client = em.find(Client.class, 1, properties);

        Assert.assertEquals(2, client.getInvoices().size());

        em.close();
        DBServiceTest.close();
    }

    @Test
    public void graphWithQuery() {
        EntityManager em = DBServiceTest.getEntityManager();

        EntityGraph graph = em.getEntityGraph(Client.GRAPH_INVOICES);

        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.name = :name", Client.class)
                .setParameter("name", "IBM")
                .setHint("javax.persistence.fetchgraph", graph);

        Client client = query.getSingleResult();

        Assert.assertEquals(Integer.valueOf(2), client.getId());
        Assert.assertEquals(1, client.getInvoices().size());

        em.close();
        DBServiceTest.close();
    }

}
