package denis.handlers.Cases;

import denis.model.Case;
import denis.model.TextMessage;
import denis.repository.CaseRepository;
import denis.states.BotState;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class CreateCaseStepThreeDescription implements CasesTemplate{

    private final CaseRepository caseRepository;

    public CreateCaseStepThreeDescription(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageService().replyWithMainMenu(TextMessage.clickCaseCreateSuccess);
        executionContext.setGlobalState(BotState.MAIN_MENU);
        localState.setDescription(executionContext.getMessage().getText());
        Case newCase = new Case();
        newCase.setUserId(executionContext.getUser().getId());
        newCase.setStatus("Нове");
        newCase.setDate(ZonedDateTime.now());
        newCase.setTitle(localState.getTitle());
        newCase.setDescription(localState.getDescription());
        caseRepository.save(newCase);
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "createCaseDesc";
    }
}
