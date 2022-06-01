package denis.handlers.Cases;

import denis.model.TextMessage;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.BotState;
import denis.states.CaseLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AllCases implements CasesTemplate{

    @Override
    public void execute(ExecutionContext executionContext, CaseLocalState localState) {
        executionContext.getReplyMessageService().replyMessage("Оберіть, що саме вас цікавить в зверненнях, натиснувши кнопку нижче:", ReplyButtonsService.newKeyboardButton(List.of(
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
        executionContext.setGlobalState(BotState.CASE_ALL);
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "default";
    }
}
