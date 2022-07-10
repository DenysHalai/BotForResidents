package denis.handlers;

import denis.model.TextMessage;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class StartHandler implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        if (executionContext.getUser().getPhoneNumber() == null) {
            executionContext.getReplyMessageServiceResident().replyMessage(TextMessage.helloMessage, ReplyButtonsService.newKeyboardButton(ButtonsTemplate.builder().title("Натисніть щоб відправити телефон").requestContact(true).build()));
        } else {
            executionContext.getReplyMessageServiceResident().replyWithMainMenu();
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

    @Override
    public MainScreen mainScreen() {
        return null;
    }
}
