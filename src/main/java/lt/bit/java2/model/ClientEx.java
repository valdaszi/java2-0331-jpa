package lt.bit.java2.model;

import javax.persistence.*;

@Entity
@Table(name = "klientai_ex")
public class ClientEx {

    private Integer id;
    private String pastaba;
    private String telefonas;
    private Client client;

    /*
        Jei anotuojami geteriai tai hibernate lauku reiksmes skaito/raso
        naudojant geterius/seterius

        Jei anotuojami laukai - tai hibernate skaito/raso tiesiai is lauku
        t.y. nenaudoja geteriu/seteriu
     */

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPastaba() {
        return pastaba;
    }

    public void setPastaba(String pastaba) {
        this.pastaba = pastaba;
    }

    public String getTelefonas() {
        return telefonas;
    }

    public void setTelefonas(String telefonas) {
        this.telefonas = telefonas;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
