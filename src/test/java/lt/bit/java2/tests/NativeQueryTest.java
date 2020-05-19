package lt.bit.java2.tests;

import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

public class NativeQueryTest {

    @Test
    public void selectClientsCount() {
        EntityManager em = DBServiceTest.getEntityManager();

        Query query = em.createNativeQuery("SELECT COUNT(*) FROM klientai");
        Object result = query.getSingleResult();

        Assert.assertEquals(BigInteger.valueOf(6), result);

        em.close();
        DBServiceTest.close();
    }
}
