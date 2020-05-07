package lt.bit.java2.tests;

import org.junit.After;
import org.junit.Before;

public class BaseTest {

    DBService dbServiceTest;

    @Before
    public void before() {
        dbServiceTest = new DBService();
    }

    @After
    public void after() {
        dbServiceTest.close();
    }
}
