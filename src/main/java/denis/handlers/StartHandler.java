package denis.handlers;

import denis.service.Buttons.ButtonsTemplate;
import denis.states.ExecutionContext;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.BotState;
import denis.model.TextMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartHandler implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        if (executionContext.getUser().getPhoneNumber() == null) {
            executionContext.getReplyMessageService().replyMessage(TextMessage.helloMessage, ReplyButtonsService.newKeyboardButton(ButtonsTemplate.builder().title("Натисніть щоб відправити телефон").requestContact(true).build()));
        } else {
            executionContext.getReplyMessageService().replyWithMainMenu();
        }
        executionContext.setGlobalState(BotState.MAIN_MENU);
    }

    @Override
    public String commandName() {
        return "/start";
    }

    @Override
    public BotState state() {
        return BotState.NEW_USER;
    }
}
