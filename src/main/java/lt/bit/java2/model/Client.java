package lt.bit.java2.model;

import lt.bit.java2.api.ClientApi;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "klientai")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    // LAZY (default OneToMany)  - t.y. join'as negeneruojamas ir
    // informacija traukima tik kai jos prireikia
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    List<Invoice> invoices;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY,
            cascade = { CascadeType.REMOVE, CascadeType.PERSIST })
    private ClientEx clientEx;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public ClientEx getClientEx() {
        return clientEx;
    }

    public void setClientEx(ClientEx clientEx) {
        this.clientEx = clientEx;
    }
}
