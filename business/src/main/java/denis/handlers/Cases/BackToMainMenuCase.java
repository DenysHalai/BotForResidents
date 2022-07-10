package denis.handlers.Cases;

import denis.states.BotState;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class BackToMainMenuCase implements TemplateCases {
    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageServiceResident().replyWithMainMenu();
        executionContext.setGlobalState(BotState.MAIN_MENU);
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "До головного меню";
    }
}
