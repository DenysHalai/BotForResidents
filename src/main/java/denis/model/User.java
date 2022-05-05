package denis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.stream.Location;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "chat_id", unique = true)
    @NotNull
    private Long chatId;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;

    @Column(name = "longitudeGeo")
    @NotNull
    private Double longitude;

    @Column(name = "latitudeGeo")
    @NotNull
    private Double latitude;

}
