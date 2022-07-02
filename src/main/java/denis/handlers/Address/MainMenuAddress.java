package denis.handlers.Address;

import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.AddressLocalState;
import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainMenuAddress implements TemplateAddress {
    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        executionContext.getReplyMessageServiceResident().replyMessage("Оберіть, що саме вас цікавить в адресах, натиснувши кнопку нижче:", ReplyButtonsService.newKeyboardButton(List.of(
                ButtonsTemplate.builder()
                        .title("Додати адресу")
                        .build(),
                ButtonsTemplate.builder()
                        .title("Список моїх адрес")
                        .webAppUrl("https://bot-vue.vercel.app/allcases?userId=" + executionContext.getUser().getId())
                        .build(),
                ButtonsTemplate.builder()
                        .title("До головного меню")
                        .build())));
        executionContext.setGlobalState(BotState.ADDRESS_ALL);
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
