package denis.handlers.Cases;

import denis.handlers.Handler;
import denis.service.Buttons.ButtonsTemplate;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.BotState;
import denis.model.TextMessage;
import denis.repository.CaseRepository;
import denis.service.ReplyMessageService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MyCase implements Handler {

    private final Map<String, CasesTemplate> casesTemplateMap = new HashMap<>();

    public MyCase(List<CasesTemplate> casesTemplateList) {
        for (CasesTemplate casesTemplate : casesTemplateList) {
            casesTemplateMap.put(casesTemplate.commandName(), casesTemplate);
        }
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        CaseLocalState localState = executionContext.getLocalState(CaseLocalState.class);
        if (localState == null) {
            localState = new CaseLocalState();
        }
        String nextStep = localState.getNextStep();
        if (nextStep == null) {
            nextStep = executionContext.getMessage().getText();
        }
        if (executionContext.getMessage().getWebAppData() != null) {
            nextStep = executionContext.getMessage().getWebAppData().getButtonText();
        }

        CasesTemplate casesTemplate = casesTemplateMap.getOrDefault(nextStep, casesTemplateMap.get("default"));
        casesTemplate.execute(executionContext, localState);
        localState.setNextStep(casesTemplate.nextStep());
        executionContext.setLocalState(localState);
    }

    @Override
    public String commandName() {
        return "Мої звернення";
    }

    @Override
    public BotState state() {
        return BotState.CASE_ALL;
    }
}
