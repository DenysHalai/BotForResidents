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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.Collections;
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
                replyMessageService.replyMessage(TextMessage.clickCaseCreateSuccess, ReplyButtonsService.mainMenuButtons());
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
                replyMessageService.replyMessage(TextMessage.clickCaseMainMenu);
                break;
            default:
                replyMessageService.replyMessage(TextMessage.clickCaseMainMenu, createCase());
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


    public static ReplyKeyboardMarkup createCase() {
        ReplyKeyboardMarkup geoButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                InlineButtons.buttonsNew("Створити звернення", false, false)
        ))));
        geoButton.setResizeKeyboard(true);
        return geoButton;
    }
}
