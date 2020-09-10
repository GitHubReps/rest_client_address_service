package example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "addresses")
@Entity
public class Address extends AbstractEntity {

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String building;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
