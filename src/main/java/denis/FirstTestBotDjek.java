package denis;

import denis.cache.UserDataCache;
import denis.googleMapsApi.GeodecodingSample;
import denis.model.*;
import denis.model.User;
import denis.repository.UserAdressRepository;
import denis.repository.UserRepository;
import denis.service.ReplyButtonsService;
import denis.service.ReplyMessageService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;
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
                /*log.info("New message from User:{}, userId: {}, chatId: {}, with text: {}",
                        message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleMessage(Message message) throws IOException {
        Optional<User> byChatId = userRepository.findByChatId(message.getChatId());
        User user;

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

        if (message.hasText() || message.hasEntities() || message.hasLocation()) {
            for (Handler handler : handlerList) {
                if (handler.state().equals(user.getBot_state()) || message.getText().equals(handler.commandName())) {
                    handler.execute(new ExecutionContext(user, replyMessageService, userRepository, message));
                    break;
                }
            }
        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            user.setName(contact.getFirstName());
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
            replyMessageService.replyMessage("Бажаєте додати адресу? Якщо так натисніть кнопку нижче:", ReplyButtonsService.newButtons("Додати адресу"));
        }/* else if (message.hasLocation()) {*//*
            user.setLatitude(message.getLocation().getLatitude());
            user.setLongitude(message.getLocation().getLongitude());
            userRepository.save(user);
            JSONObject location = param.geodecodingSample(message.getLocation().getLatitude().toString(),
                    message.getLocation().getLongitude().toString());
            String fullAdress = location.getString("formatted_address");
            replyMessageService.replyMessage(("Ваш адрес: " + fullAdress), ReplyButtonsService.newButtons("Так, це моя адреса", "Ні, адреса не вірна"));
            if (message.getText().equals("Так, це моя адреса")) {
                UserAdress userAdress = new UserAdress();
                userAdress.setUserId(message.getContact().getUserId());
                userAdress.setCountry(location.getString("country"));
                userAdress.setRegion(location.getString("administrative_area_level_1"));
                userAdress.setRegionLevel2(location.getString("administrative_area_level_2"));
                userAdress.setCity(location.getString("locality"));
                userAdress.setStreet(location.getString("route"));
                userAdress.setBuildingNumbers(location.getString("street_number"));
                userAdress.setPostalCode(location.getString("postal_code"));
                replyMessageService.replyMessage(TextMessage.successLocation);
                user.setBot_state(BotState.MAIN_MENU);
            }
        }*/ else {
            replyMessageService.replyMessage(TextMessage.erorMessage, ReplyButtonsService.newButtons("Мої звернення", "Інструкції по боту"));
        }
    }
}

//flow - sub_flow - sub_sub_flow
//Конечний автомат тг бот
//message bundle interinit
