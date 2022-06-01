package denis.handlers;

import denis.service.Buttons.ButtonsTemplate;
import denis.states.ExecutionContext;
import denis.states.BotState;
import denis.model.TextMessage;
import denis.service.Buttons.ReplyButtonsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackToMainMenu implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        executionContext.getReplyMessageService().replyWithMainMenu();
        executionContext.setGlobalState(BotState.MAIN_MENU);
    }

    @Override
    public String commandName() {
        return "До головного меню";
    }

    @Override
    public BotState state() {
        return BotState.BACK_TO_MENU;
    }
}
