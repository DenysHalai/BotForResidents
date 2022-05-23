package denis.handlers;

import denis.ExecutionContext;
import denis.service.ReplyButtonsService;
import denis.model.BotState;
import denis.model.Handler;
import denis.model.TextMessage;
import org.springframework.stereotype.Component;

@Component
public class HelpHandler implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        executionContext.getReplyMessageService().replyMessage(TextMessage.clickHelpMainMenu, ReplyButtonsService.mainMenuButtons());
        executionContext.setGlobalState(BotState.MAIN_MENU);
    }

    @Override
    public String commandName() {
        return "Інструкції по боту";
    }

    @Override
    public BotState state() {
        return BotState.HELP_MENU;
    }
}
