package denis.handlers.Cases;

import denis.states.BotState;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class CasesBackToMainMenu implements CasesTemplate{
    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageService().replyWithMainMenu();
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
