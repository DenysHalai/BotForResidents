package denis.handlers.Address;

import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.AddressLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddPushGeoVerify implements TemplateAddress {

    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        if ("Так, це моя адреса".equals(executionContext.getMessage().getText())){
            executionContext.getReplyMessageServiceResident().replyMessage("Це багатоквартирний будинок?", ReplyButtonsService.newKeyboardButton(List.of(
                    ButtonsTemplate.builder()
                            .title("Так")
                            .build(),
                    ButtonsTemplate.builder()
                            .title("Ні")
                            .build())));
            threadLocal.set("Обрав тип будинку");
        } else {
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
        }
    }

    @Override
    public String nextStep() {
        String s = threadLocal.get();
        threadLocal.remove();
        return s;
    }

    @Override
    public String commandName() {
        return "Підтверждення адреси";
    }
}
