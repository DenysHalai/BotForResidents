package denis;

import denis.cache.UserDataCache;
import denis.model.Handler;
import denis.model.TextMessage;
import denis.model.User;
import denis.repository.UserRepository;
import denis.service.InlineLocationModeService;
import denis.service.ReplyButtonsService;
import denis.service.ReplyMessageService;
import denis.states.BotState;
import denis.states.ExecutionContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;

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

    @Autowired
    private InlineLocationModeService inlineLocationMode;

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
            } else if (update.hasInlineQuery()) {
                handleInlineQuery(update.getInlineQuery());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInlineQuery(InlineQuery inlineQuery) {
        List<InlineQueryResult> inlineQueryResults = inlineLocationMode.execute(inlineQuery);
        try {
            execute(AnswerInlineQuery.builder().inlineQueryId(inlineQuery.getId()).results(inlineQueryResults).cacheTime(5).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        ExecutionContext executionContext = new ExecutionContext(user, replyMessageService, userRepository, message);

        // проверка наличия телефона у юзера в БД
        if (user.getPhoneNumber() == null) {
            user.setBot_state(BotState.NEW_USER);
            userRepository.save(user);
        }

        if (!executionContext.getWebAppData().isEmpty()) {
            String state = executionContext.getWebAppData().get("state");
            if (state != null) {
                user.setBot_state(BotState.valueOf(state));
            }
        }

        if (message.hasText() || message.hasEntities() || message.hasLocation() || user.getBot_state().equals(BotState.ADDRESS_ALL) || message.getWebAppData() != null) {
            for (Handler handler : handlerList) {
                if (handler.state().equals(user.getBot_state()) || handler.commandName().equals(message.getText())) {
                    handler.execute(executionContext);
                    break;
                }
            }
        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            user.setName(contact.getFirstName());
            user.setPhoneNumber(contact.getPhoneNumber());
            userRepository.save(user);
            replyMessageService.replyMessage("Бажаєте додати адресу? Якщо так натисніть кнопку нижче:", ReplyButtonsService.newWebApp("Додати адресу", "https://bot-vue.vercel.app/location"));
            executionContext.setGlobalState(BotState.ADDRESS_ALL);
        } else {
            replyMessageService.replyMessage(TextMessage.erorMessage, ReplyButtonsService.newWebAppAndButtons("Мої звернення", "https://bot-vue.vercel.app/allcases?userId=" + user.getId(), "Інструкції по боту"));
        }
    }
}

//flow - sub_flow - sub_sub_flow
//Конечний автомат тг бот
//message bundle interinit
