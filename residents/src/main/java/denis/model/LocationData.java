package denis.model;

import lombok.Value;

@Value
public class LocationData {
    String cityType;
    String city;
    String streetType;
    String street;
    String number;
}
