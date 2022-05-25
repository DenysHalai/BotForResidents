package denis;

import lombok.Data;
import java.util.Map;

@Data
public class AdressLocalState {
    String nextStep;
    Map<String, Object> location;
}
