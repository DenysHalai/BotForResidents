package denis.cache;

import denis.model.BotState;
import denis.model.User;
import denis.repository.UserRepository;

public class UserDataCache {
    private UserRepository userRepository;

    public void setUserCurrentBotState(User user, BotState botState) {
        if (user.getBot_state() == null) {
            user.setBot_state(BotState.NEW_USER);
        } else {
            user.setBot_state(botState);
            userRepository.save(user);
        }
    }

    public BotState getUserCurrentBotState(Long chatId) {
        return userRepository.findByChatId(chatId).get().getBot_state();
    }
}
