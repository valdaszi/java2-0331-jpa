package lt.bit.java2.model;

import javax.persistence.*;

@Entity
@Table(name = "klientai_ex")
public class ClientEx {

    @Id
    private Integer id;

    private String pastaba;
    private String telefonas;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Client client;

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
