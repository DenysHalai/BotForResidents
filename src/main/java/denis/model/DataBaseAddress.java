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
    private String region;

    @Column(name = "district")
    private String district;

    @Column(name = "title")
    private String title;

    @Column(name = "zip")
    private String zip;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String number;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;
}
