package denis.handlers.Address;

import com.fasterxml.jackson.databind.ObjectMapper;
import denis.service.FindAddressInDataBaseService;
import denis.states.AddressLocalState;
import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddressWebApp implements TemplateAddress {

    private final FindAddressInDataBaseService findAddressInDataBaseService;

    public AddressWebApp(FindAddressInDataBaseService findAddressInDataBaseService) {
        this.findAddressInDataBaseService = findAddressInDataBaseService;
    }

    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> map = mapper.readValue(executionContext.getMessage().getWebAppData().getData(), Map.class);
            findAddressInDataBaseService.tryFindAddress(map.get("city"), map.get("street"), map.get("number"), executionContext.getUser().getId(), map.get("numberApart"));
            executionContext.getReplyMessageServiceResident().replyWithMainMenu();
            executionContext.setGlobalState(BotState.MAIN_MENU);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "Додати адресу за допомогою WebApp";
    }
}
