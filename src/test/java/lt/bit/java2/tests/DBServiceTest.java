package lt.bit.java2.tests;

import lt.bit.java2.services.DBService;

public class DBServiceTest extends DBService {

    static public void close() {
        emf.close();
        emf = null;
    }
}
