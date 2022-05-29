package denis.states;

import com.fasterxml.jackson.databind.ObjectMapper;
import denis.model.DataBaseAddress;
import denis.model.User;
import lombok.Value;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

@Value
public class LocationContext {
    ObjectMapper mapper = new ObjectMapper();
    DataBaseAddress dataBaseAddress;
    User user;
    InlineQuery inlineQuery;

    public String setLocationInlineQuery(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
