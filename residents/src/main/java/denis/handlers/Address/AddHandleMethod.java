package denis.handlers.Address;

import denis.service.Buttons.InlineButtonsService;
import denis.states.AddressLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class AddHandleMethod implements TemplateAddress {
    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        executionContext.getReplyMessageServiceResident().hideButtons();
        executionContext.getReplyMessageServiceResident().replyMessage("Натисніть на кнопку нижче, щоб почати вводити адресу:",
                InlineButtonsService.inlineKeyboardQueryCurrentChat("Ввести адресу"));
    }

    @Override
    public String nextStep() {
        return "Обрав адресу Inline";
    }

    @Override
    public String commandName() {
        return "Вказати адресу вручну";
    }
}
