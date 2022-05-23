package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Case {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "description")
    @NotNull
    private String description;

}
