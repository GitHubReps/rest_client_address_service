package example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "clients")
@Entity
public class Client extends AbstractEntity {

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "client")
    private List<Address> addresses;

}
