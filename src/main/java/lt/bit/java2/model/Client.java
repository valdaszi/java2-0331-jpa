package lt.bit.java2.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "klientai")
@Data
@NamedQuery(name = Client.QUERY_ALL, query = "SELECT c FROM Client c")
// @NamedQuery(...
// @NamedQuery(...
@NamedEntityGraph(name = Client.GRAPH_INVOICES,
        attributeNodes = {
            @NamedAttributeNode("invoices"),
            @NamedAttributeNode("clientEx")
        })
// @NamedEntityGraph(...
// @NamedEntityGraph(...
public class Client {

    public static final String QUERY_ALL = "Client.query.all";
    public static final String GRAPH_INVOICES = "Client.graph.invoices";

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
            cascade = {
                    CascadeType.REMOVE, // salinant Client bus pasalintas ir ClientEx
                    CascadeType.PERSIST // leidzia kurti naujus Client ir ClientEx vienu metu saugojant tiktai Client
            },
            orphanRemoval = true)
    private ClientEx clientEx;

}
