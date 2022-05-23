package denis;

import denis.cache.UserDataCache;
import denis.model.BotState;
import denis.model.Handler;
import denis.model.TextMessage;
import denis.model.User;
import denis.repository.UserRepository;
import denis.service.ReplyButtonsService;
import denis.service.ReplyMessageService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class FirstTestBotDjek extends TelegramLongPollingBot {

    @Value("${bot.name}")
    @Getter
    private String botUsername;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    List<Handler> handlerList = new ArrayList<>();

    private UserDataCache userDataCache;

    @Value("${bot.token}")
    @Getter
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message message = update.getMessage();
            if (update.hasMessage()) {
                handleMessage(update.getMessage());
                log.info("New message from User:{}, userId: {}, chatId: {}, with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleMessage(Message message) {
        Optional<User> byChatId = userRepository.findByChatId(message.getChatId());
        User user;
        String inputMessage = message.getText();

        // проверка наличия юзера в БД
        if (byChatId.isPresent()) {
            user = byChatId.get();
        } else {
            user = new User();
            user.setChatId(message.getChatId());
            user.setUserId(message.getFrom().getId());
            userRepository.save(user);
        }
        ReplyMessageService replyMessageService = new ReplyMessageService(user.getChatId(), this);

        // проверка наличия телефона у юзера в БД
        if (user.getPhoneNumber() == null) {
            user.setBot_state(BotState.NEW_USER);
            userRepository.save(user);
        }

        if (message.hasText() || message.hasEntities()) {
            for (Handler handler : handlerList) {
                if (inputMessage.equals(handler.commandName()) || handler.state().equals(user.getBot_state())) {
                    handler.execute(new ExecutionContext(user, replyMessageService, userRepository, message));
                    break;
                }
            }
        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            user.setName(contact.getFirstName());
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
            replyMessageService.replyMessage(TextMessage.sendLocation, ReplyButtonsService.geoButton());
        } else if (message.hasLocation()) {
            user.setLatitude(message.getLocation().getLatitude());
            user.setLongitude(message.getLocation().getLongitude());
            userRepository.save(user);
            replyMessageService.replyMessage(TextMessage.successLocation);
        } else {
            replyMessageService.replyMessage(TextMessage.erorMessage, ReplyButtonsService.mainMenuButtons());
        }
    }
}

//flow - sub_flow - sub_sub_flow
//Конечний автомат тг бот
//message bundle interinit
