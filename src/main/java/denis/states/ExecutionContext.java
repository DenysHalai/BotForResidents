package denis.states;

import com.fasterxml.jackson.databind.ObjectMapper;
import denis.model.User;
import denis.repository.UserRepository;
import denis.service.ReplyMessageServiceResident;
import lombok.Value;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;

@Value
public class ExecutionContext {
    ObjectMapper mapper = new ObjectMapper();
    User user;
    ReplyMessageServiceResident replyMessageServiceResident;
    UserRepository userRepository;
    Message message;


    public void setGlobalState(BotState state) {
        user.setBot_state(state);
        if (state == BotState.MAIN_MENU){
            setLocalState(null);
        }
        userRepository.save(user);
    }

    public void setLocalState(String state) {
        user.setLocal_state(state);
        userRepository.save(user);
    }

    public String getLocalState() {
        return this.user.getLocal_state();
    }

    public <T> T getLocalState(Class<T> tClass) {
        try {
            String localState = getLocalState();
            if (localState == null) {
                return null;
            }
            return mapper.readValue(localState, tClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setLocalState(Object o) {
        try {
            String x = null;
            if (o != null) {
                x = mapper.writeValueAsString(o);
            }
            setLocalState(x);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getWebAppData(){
        if (message.getWebAppData() != null){
            try {
                return mapper.readValue(message.getWebAppData().getData(), Map.class);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return new HashMap<>();
    }
}

// Object mapper
// Generic
