package lt.bit.java2.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "klientai")
@Data
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
            cascade = {
                    CascadeType.REMOVE, // salinant Client bus pasalintas ir ClientEx
                    CascadeType.PERSIST // leidzia kurti naujus Client ir ClientEx vienu metu saugojant tiktai Client
            },
            orphanRemoval = true)
    private ClientEx clientEx;

}
