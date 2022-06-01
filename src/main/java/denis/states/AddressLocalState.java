package denis.states;

import denis.model.LocationData;
import lombok.Data;

@Data
public class AddressLocalState {
    String nextStep;
    LocationData location;
}
