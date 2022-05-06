package denis;

import denis.model.Icon;
import denis.model.User;
import denis.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
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
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText() || message != null && message.hasContact() || message != null && message.hasLocation()) {
            log.info("New message from User:{}, chatId: {}, with text: {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            if (update.hasMessage()) {
                try {
                    handleMessage(update.getMessage());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleMessage(Message message) throws TelegramApiException {
        Optional<User> byChatId = userRepository.findByChatId(message.getChatId());
        User user;
        if (byChatId.isPresent()) {
            user = byChatId.get();
        } else {
            user = new User();
            user.setChatId(message.getChatId());
            userRepository.save(user);
        }
        switch (message.getText()){
            case "/start":
        }
        if (message.hasContact()) {
            Contact contact = message.getContact();
            user.setName(contact.getFirstName());
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
            execute(
                    SendMessage
                            .builder()
                            .text("Дякую, ваш номер успішно записаний" + "\n" +
                                    "Наступний крок реєстрація вашого житла!\nДля цього вам буде достатньо відправити мені геолокацію з МОБІЛЬНОГО телефону — а я вже визначу адресу. Але зауважте, що геолокацію можливо відправити ВИКЛЮЧНО з мобільного \uD83D\uDCCD" + "\n" +
                                    "\n" +
                                    "\uD83D\uDD39 Якщо ви зараз знаходитесь за адресою, яку хочете додати, просто відправте мені вашу поточну геолокацію, натиснувши на відповідну кнопку\n" +
                                    "\uD83D\uDD39 Якщо ви зараз НЕ знаходитесь за адресою, натисніть на кнопку \"Ввести адресу вручну\" і вкажіть адресу вашого житла за допомогою клавіатури")
                            .chatId(message.getChatId().toString())
                            .replyMarkup(geoButton())
                            .build()
            );
        }

        if (message.hasLocation()) {
            user.setLatitude(message.getLocation().getLatitude());
            user.setLongitude(message.getLocation().getLongitude());
            userRepository.save(user);
            execute(
                    SendMessage.builder()
                            .text("Дякую, ваша геолокація успішно записана")
                            .chatId(message.getChatId().toString())
                            .replyMarkup(new ReplyKeyboardRemove(true))
                            .build());
        }

        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity = message.getEntities().stream().filter(messageEntity -> "bot_command".equals(messageEntity.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message
                        .getText()
                        .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/start":
                        if (user.getPhoneNumber() == null) {
                            execute(
                                    SendMessage.builder()
                                            .text("Привіт! \uD83D\uDC4B\n" +
                                                    "\n" +
                                                    "Мене звати Шрек, і я — чатбот для мешканців, чиї будинки знаходяться в управлінні ОСББ або управляючих компаній \uD83E\uDD16\n" +
                                                    "\n" +
                                                    "Для початку, поділіться, будь ласка, зі мною вашим номером телефону, натиснувши на кнопку під цим повідомленням \uD83D\uDC47\n" +
                                                    "\n" +
                                                    "Зауважте, що поділитися номером можна лише з мобільної або десктопної версії Телеграма — з веб-версії (в браузері) це зробити неможливо ❗️")
                                            .chatId(message.getChatId().toString())
                                            .replyMarkup(startButton())
                                            .build());
                        } else {
                            execute(
                                    SendMessage.builder()
                                            .text("Вибачте, але я вас не зрозумів. Щоб продовжити, натисніть одну з КНОПОК нижче \uD83D\uDC47")
                                            .chatId(message.getChatId().toString())
                                            .replyMarkup(mainMenuButtons())
                                            .build());
                        }
                }
            }
        }
    }


    public static ReplyKeyboardMarkup startButton() {
        ReplyKeyboardMarkup startButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew(Icon.PHONE.get() + " Натисніть щоб поділитися телефоном", true, false)
        ))));
        startButton.setResizeKeyboard(true);
        return startButton;
    }

    public static ReplyKeyboardMarkup geoButton() {
        ReplyKeyboardMarkup geoButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew("Натисніть щоб поділитися своєю геолокацією", false, true)
        ))));
        geoButton.setResizeKeyboard(true);
        return geoButton;
    }

    public static KeyboardButton buttonsNew(String titleButtons, boolean requestContact, boolean requestGeo) {
        KeyboardButton buttonFirst = new KeyboardButton(titleButtons);
        buttonFirst.setRequestContact(requestContact);
        buttonFirst.setRequestLocation(requestGeo);
        return buttonFirst;
    }

    public static InlineKeyboardMarkup inlineKeyboardOne() {
        InlineKeyboardMarkup inlineKeyboardOne = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Ввести адресу вручну");
        inlineKeyboardButton1.setCallbackData("123");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardOne.setKeyboard(rowList);
        return inlineKeyboardOne;
    }

    public static ReplyKeyboardMarkup mainMenuButtons() {
        ReplyKeyboardMarkup mainMenuButtons = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew("Мої зверення", false, false),
                buttonsNew("Мої адреси", false, false),
                buttonsNew("Доступні послуги", false, false),
                buttonsNew("Інструкції по боту", false, false)
        ))));
        mainMenuButtons.setResizeKeyboard(true);
        return mainMenuButtons;
    }
}

//flow - sub_flow - sub_sub_flow
//Конечний автомат тг бот
