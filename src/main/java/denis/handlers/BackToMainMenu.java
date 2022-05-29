package denis.handlers;

import denis.states.ExecutionContext;
import denis.states.BotState;
import denis.model.Handler;
import denis.model.TextMessage;
import denis.service.ReplyButtonsService;
import org.springframework.stereotype.Component;

@Component
public class BackToMainMenu implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        executionContext.getReplyMessageService().replyMessage(TextMessage.clickCaseMainMenu, ReplyButtonsService.newButtons("Мої звернення", "Інструкції по боту"));
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
