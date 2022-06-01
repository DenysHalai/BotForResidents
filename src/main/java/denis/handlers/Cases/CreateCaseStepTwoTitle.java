package denis.handlers.Cases;

import denis.model.TextMessage;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class CreateCaseStepTwoTitle implements CasesTemplate{
    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageService().replyMessage(TextMessage.clickCaseCreateStep2);
        localState.setTitle(executionContext.getMessage().getText());
    }

    @Override
    public String nextStep() {
        return "createCaseDesc";
    }

    @Override
    public String commandName() {
        return "createCaseTitle";
    }
}
