package lt.bit.java2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pardavimai")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data")
    private LocalDate date;

//    @Column(name = "kliento_id")
//    private Integer clientId;


    @JsonIgnore // Jackson anotacija kuri reiksia kad sio lauko nereikia serializuoti
    // EAGER (default ManyToOne) - visada generuojamas select su join
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kliento_id")
    private Client client;

    @Column(name = "suma")
    private BigDecimal sum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

//    public Integer getClientId() {
//        return clientId;
//    }
//
//    public void setClientId(Integer clientId) {
//        this.clientId = clientId;
//    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
