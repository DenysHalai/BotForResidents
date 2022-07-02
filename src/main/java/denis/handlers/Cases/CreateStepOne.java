package denis.handlers.Cases;

import denis.model.TextMessage;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class CreateStepOne implements TemplateCases {

    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageServiceResident().replyMessage(TextMessage.clickCaseCreateStep1);
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
