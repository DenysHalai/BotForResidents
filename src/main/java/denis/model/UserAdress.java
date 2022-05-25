package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "userAdress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAdress {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "country")
    @NotNull
    private String country;

    @Column(name = "region")
    @NotNull
    private String region;

    @Column(name = "regionLevel2")
    @NotNull
    private String regionLevel2;

    @Column(name = "city")
    @NotNull
    private String city;

    @Column(name = "street")
    @NotNull
    private String street;

    @Column(name = "buildingNumbers")
    @NotNull
    private String buildingNumbers;

    @Column(name = "flatNumbers")
    @NotNull
    private String flatNumbers;

    @Column(name = "postalCode")
    @NotNull
    private String postalCode;

}
