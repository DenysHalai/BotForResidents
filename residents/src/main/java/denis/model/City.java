package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "city")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "city")
    private Set<Street> streets;
}

