package denis.service.Buttons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ButtonsTemplate {
    String title;
    boolean requestContact;
    boolean requestLocation;
    String webAppUrl;
}
