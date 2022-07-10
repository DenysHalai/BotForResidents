package denis.handlers;

import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class BackToMainMenu implements Handler {
    @Override
    public void execute(ExecutionContext executionContext) {
        executionContext.getReplyMessageServiceResident().replyWithMainMenu();
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

    @Override
    public MainScreen mainScreen() {
        return null;
    }
}
