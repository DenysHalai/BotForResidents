package denis.handlers.Address;

import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.AddressLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddVerify implements TemplateAddress {
    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        executionContext.getReplyMessageServiceResident().replyMessage("Це багатоквартирний будинок?", ReplyButtonsService.newKeyboardButton(List.of(
                ButtonsTemplate.builder()
                        .title("Так")
                        .build(),
                ButtonsTemplate.builder()
                        .title("Ні")
                        .build())));
        String[] split = executionContext.getMessage().getText().split("#");
        Long addressId = Long.valueOf(split[split.length - 1]);
        localState.setAddressId(addressId);
    }

    @Override
    public String nextStep() {
        return "Обрав тип будинку";
    }

    @Override
    public String commandName() {
        return "Обрав адресу Inline";
    }
}
