package denis.handlers.Cases;

import denis.model.Case;
import denis.model.TextMessage;
import denis.repository.CaseRepository;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class CasesWebApp implements TemplateCases {

    private final CaseRepository caseRepository;

    public CasesWebApp(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        Case newCase = new Case();
        newCase.setDescription(executionContext.getWebAppData().get("description"));
        newCase.setTitle(executionContext.getWebAppData().get("title"));
        newCase.setDate(ZonedDateTime.now());
        newCase.setUserId(Long.valueOf(executionContext.getWebAppData().get("userId")));
        newCase.setStatus("Нове");
        caseRepository.save(newCase);
        executionContext.getReplyMessageServiceResident().replyMessage(TextMessage.clickCaseCreateSuccess, ReplyButtonsService.newKeyboardButton(List.of(
                ButtonsTemplate.builder()
                        .title("Створити звернення")
                        .build(),
                ButtonsTemplate.builder()
                        .title("Список моїх зверненнь")
                        .webAppUrl("https://bot-vue.vercel.app/allcases?userId=" + executionContext.getUser().getId())
                        .build(),
                ButtonsTemplate.builder()
                        .title("До головного меню")
                        .build())));
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "Список моїх зверненнь";
    }
}
