package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "zip")
    private String zip;

    @Column(name = "number")
    private String number;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "street_id")
    private Street street;
}
