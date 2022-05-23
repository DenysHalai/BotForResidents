package denis;

import com.fasterxml.jackson.databind.ObjectMapper;
import denis.model.BotState;
import denis.model.User;
import denis.repository.UserRepository;
import denis.service.ReplyMessageService;
import lombok.Value;
import org.telegram.telegrambots.meta.api.objects.Message;

@Value
public class ExecutionContext {
    ObjectMapper mapper = new ObjectMapper();
    User user;
    ReplyMessageService replyMessageService;
    UserRepository userRepository;
    Message message;


    public void setGlobalState(BotState state) {
        user.setBot_state(state);
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
            setLocalState(mapper.writeValueAsString(o));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

// Object mapper
// Generic
