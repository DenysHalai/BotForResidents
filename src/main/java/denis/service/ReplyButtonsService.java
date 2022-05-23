package denis.service;

import denis.FirstTestBotDjek;
import denis.InlineButtons;
import denis.model.Icon;
import denis.model.User;
import denis.repository.UserRepository;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

public class ReplyButtonsService {
    private final User user;
    private final FirstTestBotDjek bot;
    private  final UserRepository userRepository;

    public ReplyButtonsService(User user, FirstTestBotDjek bot, UserRepository userRepository) {
        this.user = user;
        this.bot = bot;
        this.userRepository = userRepository;
    }

    public static ReplyKeyboardMarkup startButton() {
        ReplyKeyboardMarkup startButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                InlineButtons.buttonsNew(Icon.PHONE.get() + " Натисніть щоб поділитися телефоном", true, false)
        ))));
        startButton.setResizeKeyboard(true);
        return startButton;
    }

    public static ReplyKeyboardMarkup geoButton() {
        ReplyKeyboardMarkup geoButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                InlineButtons.buttonsNew("Поділитися своєю геолокацією", false, true),
                InlineButtons.buttonsNew("Вказати адресу вручну", false, false)
        ))));
        geoButton.setResizeKeyboard(true);
        return geoButton;
    }

    public static ReplyKeyboardMarkup mainMenuButtons() {
        ReplyKeyboardMarkup mainMenuButtons = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                InlineButtons.buttonsNew("Мої звернення"),
                InlineButtons.buttonsNew("Інструкції по боту", false, false)
        ))));
        mainMenuButtons.setResizeKeyboard(true);
        return mainMenuButtons;
    }
}





/*


    @Autowired
    private final FirstTestBotDjek bot;

    public MainHandler(FirstTestBotDjek bot) {
        this.bot = bot;
    }

    public void handleMessage(Message message) {
        Optional<User> byChatId = userRepository.findByChatId(message.getChatId());
        User user;
        if (byChatId.isPresent()) {
            user = byChatId.get();
        } else {
            user = new User();
            user.setChatId(message.getChatId());
            user.setUserId(message.getFrom().getId());
            userRepository.save(user);
        }

        ReplyMessageService replyMessageCreate = new ReplyMessageService(message.getChatId(), this.bot);

        if (message.hasContact()) {
            Contact contact = message.getContact();
            user.setName(contact.getFirstName());
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
            replyMessageCreate.replyMessage(TextMessage.sendLocation, geoButton());
        }

        if (message.hasLocation()) {
            user.setLatitude(message.getLocation().getLatitude());
            user.setLongitude(message.getLocation().getLongitude());
            userRepository.save(user);
            replyMessageCreate.replyMessage(TextMessage.successLocation);
        }

        if (message.hasText() && !message.hasEntities()) {
            String command = message
                    .getText();
            switch (command) {
                case "Мої звернення" -> replyMessageCreate.replyMessage(TextMessage.clickCaseMainMenu);
                case "Інструкції по боту" -> replyMessageCreate.replyMessage(TextMessage.clickHelpMainMenu, mainMenuButtons());
            }
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
                            replyMessageCreate.replyMessage(TextMessage.helloMessage, startButton());
                        }
                        replyMessageCreate.replyMessage(TextMessage.erorMessage, mainMenuButtons());
                        break;
                }
            }
        }
    }*/