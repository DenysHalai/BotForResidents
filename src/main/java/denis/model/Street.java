package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "street")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Street {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne (optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}

