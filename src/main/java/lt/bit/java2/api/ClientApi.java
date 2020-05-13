package lt.bit.java2.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.bit.java2.model.Client;
import lt.bit.java2.services.DBService;
import lt.bit.java2.services.JsonService;

import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface ClientApi {

    // static Logger LOG = Logger.getLogger("lt.bit.java2.api.ClientApi");
    static Logger LOG = Logger.getLogger(ClientApi.class.getName());

    public static void main(String[] args) throws JsonProcessingException {        Logger.getLogger("").setLevel(Level.WARNING);
        Logger.getLogger("").setLevel(Level.WARNING);

        // 1

        EntityManager em = DBService.getEntityManager();
        Client client = em.find(Client.class, 1);

        // System.out.println("--> " + client.getName());
        LOG.fine("--> " + client.getName() + " " + client.getId());
        LOG.warning("--> " + client.getName());

        // cia, t.y. pries entityManager uzdaryma galima bandyti serializuoti
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//
//        String json = mapper.writeValueAsString(client);
//        System.out.println(json);

        em.close();

        // arba cia, t.y. po:
        // gauname klaida, nes po entityManager uzdarymo negalima traukti Lazy rysio objektu
        // reikia panaudoti plugina kuris supranta hibernate lazy objektus:
        // group: 'com.fasterxml.jackson.datatype', name: 'jackson-datatype-hibernate5'
        // ir uzregistruoti 'Hibernate5Module()'

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        mapper.registerModule(new Hibernate5Module());

        ObjectMapper mapper = JsonService.getMapper();

        String json = mapper.writeValueAsString(client);
        System.out.println(json);
    }

}
