package denis.handlers.Cases;

import denis.handlers.Handler;
import denis.handlers.MainScreen;
import denis.states.BotState;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainCases implements Handler {

    private final Map<String, TemplateCases> casesTemplateMap = new HashMap<>();

    public MainCases(List<TemplateCases> casesTemplateList) {
        for (TemplateCases casesTemplate : casesTemplateList) {
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

        TemplateCases casesTemplate = casesTemplateMap.getOrDefault(nextStep, casesTemplateMap.get("default"));
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

    @Override
    public MainScreen mainScreen() {
        return null;
    }
}
