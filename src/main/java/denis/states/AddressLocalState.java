package denis.states;

import denis.controllers.LocationData;
import lombok.Data;
import java.util.Map;

@Data
public class AddressLocalState {
    String nextStep;
    LocationData location;
}
