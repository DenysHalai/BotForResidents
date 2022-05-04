package denis;

import denis.model.User;
import denis.repository.UserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
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

    @Value("${bot.token}")
    @Getter
    private String botToken;

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void handleMessage(Message message) throws TelegramApiException {
        if (message.hasContact()) {
            Contact contact = message.getContact();
            System.out.println(contact.getPhoneNumber());
            User entity = new User();
            entity.setChatId(message.getChatId());
            entity.setName(contact.getFirstName());
            entity.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(entity);
        }

        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(messageEntity -> "bot_command".equals(messageEntity.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message
                        .getText()
                        .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/start":
                        if (userRepository.(message.getChatId()).isPresent()) {
                            execute(
                                    SendMessage.builder()
                                            .text("Будь-ласка поділіться вашим номером телефону натиснувши на кнопку:")
                                            .chatId(message.getChatId().toString())
                                            .replyMarkup(startButton())
                                            .build());
                        } else {
                            execute(
                                    SendMessage.builder()
                                            .text("Оберіть будь-ласка що вас цікавить")
                                            .chatId(message.getChatId().toString())
                                            .build());
                        }
                }
            }
        }
    }

    public chatIdNew(Long chatId){
        User entity = new User();
        entity.setChatId(chatId);
        userRepository.
        return;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static ReplyKeyboardMarkup startButton() {
        ReplyKeyboardMarkup startButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew("Натисніть щоб поділитися телефоном", true, false)
        ))));
        startButton.setResizeKeyboard(true);
        return startButton;
    }

    public static KeyboardButton buttonsNew(String titleButtons, boolean requestContact, boolean requestGeo) {
        KeyboardButton buttonFirst = new KeyboardButton(titleButtons);
        buttonFirst.setRequestContact(requestContact);
        buttonFirst.setRequestLocation(requestGeo);
        return buttonFirst;
    }
}


