package denis.handlers.Address;

import denis.states.AddressLocalState;
import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class BackToMainMenuAddress implements TemplateAddress {

    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
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
