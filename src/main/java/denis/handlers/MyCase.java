package denis.handlers;

import denis.CaseLocalState;
import denis.ExecutionContext;
import denis.InlineButtons;
import denis.service.ReplyButtonsService;
import denis.model.BotState;
import denis.model.Case;
import denis.model.Handler;
import denis.model.TextMessage;
import denis.repository.CaseRepository;
import denis.service.ReplyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyCase implements Handler {

    @Autowired
    private CaseRepository caseRepository;

    @Override
    public void execute(ExecutionContext executionContext) {
        CaseLocalState localState = executionContext.getLocalState(CaseLocalState.class);
        ReplyMessageService replyMessageService = executionContext.getReplyMessageService();
        if (localState == null) {
            localState = new CaseLocalState();
        }
        String nextStep = localState.getNextStep();
        if (nextStep == null) {
            nextStep = "";
        } else if (nextStep.equals("allCases")) {
            nextStep = executionContext.getMessage().getText();
        }
        switch (nextStep) {
            case "Створити звернення": {
                replyMessageService.replyMessage(TextMessage.clickCaseCreateStep1);
                localState.setNextStep("createCaseTitle");
                break;
            }
            case "createCaseTitle": {
                replyMessageService.replyMessage(TextMessage.clickCaseCreateStep2);
                localState.setTitle(executionContext.getMessage().getText());
                localState.setNextStep("createCaseDesc");
                break;
            }
            case "createCaseDesc": {
                replyMessageService.replyMessage(TextMessage.clickCaseCreateSuccess, ReplyButtonsService.newButtons("Мої звернення", "Інструкції по боту"));
                executionContext.setGlobalState(BotState.MAIN_MENU);
                localState.setDescription(executionContext.getMessage().getText());
                Case newCase = new Case();
                newCase.setUserId(executionContext.getUser().getUserId());
                newCase.setTitle(localState.getTitle());
                newCase.setDescription(localState.getDescription());
                caseRepository.save(newCase);
                localState.setNextStep(null);
                break;
            }
            case "Список всіх зверненнь":
                replyMessageService.replyMessage(oneCases(executionContext.getUser().getUserId()), ReplyButtonsService.newButtons("Створити звернення", "До головного меню"));
                executionContext.setGlobalState(BotState.CASE_ALL);
                break;
            default:
                replyMessageService.replyMessage(TextMessage.clickCaseMainMenu, ReplyButtonsService.newButtons("Створити звернення", "Список всіх зверненнь"));
                executionContext.setGlobalState(BotState.CASE_ALL);
                localState.setNextStep("allCases");
        }
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

    public String oneCases(Long userId) {
        Iterable<Case> caseList = caseRepository.findByUserId(userId);
        List<String> casesListString = new ArrayList<>();
        int count = 1;
        for (Case elem : caseList) {
            casesListString.add(count + "." + " " + elem.getTitle() + " - " + elem.getDescription() + "\n");
            count++;
        }

        StringBuilder sb = new StringBuilder();
        for (String elem : casesListString) {
            sb.append(elem);
        }
        return sb.toString();
    }
}
