package denis.handlers.Cases;

import denis.model.TextMessage;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class CreateCaseStepOne implements CasesTemplate{

    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageService().replyMessage(TextMessage.clickCaseCreateStep1);
    }

    @Override
    public String nextStep() {
        return "createCaseTitle";
    }

    @Override
    public String commandName() {
        return "Створити звернення";
    }
}
