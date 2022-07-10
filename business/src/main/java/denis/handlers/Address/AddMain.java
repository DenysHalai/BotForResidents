package denis.handlers.Address;

import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.AddressLocalState;
import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddMain implements TemplateAddress {
    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        executionContext.getReplyMessageServiceResident().replyMessage("Оберіть яким чином вам зручно додати нову адресу:", ReplyButtonsService.newKeyboardButton(List.of(
                ButtonsTemplate.builder()
                        .title("Поділитися своєю геолокацією")
                        .requestLocation(true)
                        .build(),
                ButtonsTemplate.builder()
                        .title("Вказати адресу вручну")
                        .build(),
                ButtonsTemplate.builder()
                        .title("Додати адресу через сайт")
                        .webAppUrl("https://bot-vue.vercel.app/location")
                        .build())));
        executionContext.setGlobalState(BotState.ADDRESS_ALL);
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "Додати адресу";
    }
}
