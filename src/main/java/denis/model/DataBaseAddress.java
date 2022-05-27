package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dataBaseAddress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataBaseAddress {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "region")
    @NotNull
    private String region;

    @Column(name = "district")
    @NotNull
    private String district;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "zip")
    @NotNull
    private String zip;

    @Column(name = "street")
    @NotNull
    private String street;

    @Column(name = "number")
    @NotNull
    private String number;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;
}
