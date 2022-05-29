package denis.handlers;

import denis.states.ExecutionContext;
import denis.service.ReplyButtonsService;
import denis.model.Handler;
import denis.states.BotState;
import denis.model.TextMessage;
import org.springframework.stereotype.Component;

@Component
public class StartHandler implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        if (executionContext.getUser().getPhoneNumber() == null) {
            executionContext.getReplyMessageService().replyMessage(TextMessage.helloMessage, ReplyButtonsService.startButton());
        } else {
            executionContext.getReplyMessageService().replyMessage(TextMessage.erorMessage, ReplyButtonsService.newWebApp("Приложение"));
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
