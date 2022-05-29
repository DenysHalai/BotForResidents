package denis.states;

import lombok.Data;
import java.util.Map;

@Data
public class AddressLocalState {
    String nextStep;
    Map<String, Object> location;
}
