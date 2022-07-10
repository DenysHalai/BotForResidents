package denis.handlers.Address;

import denis.handlers.Handler;
import denis.handlers.MainScreen;
import denis.model.UserAddress;
import denis.repository.UserAddressRepository;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.AddressLocalState;
import denis.states.BotState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainAddress implements Handler {

    private final Map<String, TemplateAddress> addressTemplateMap = new HashMap<>();

    public MainAddress(List<TemplateAddress> addressTemplatesList){
        for (TemplateAddress addressTemplate : addressTemplatesList) {
            addressTemplateMap.put(addressTemplate.commandName(), addressTemplate);
        }
    }

    @Override
    public void execute(ExecutionContext executionContext) {
        AddressLocalState localState = executionContext.getLocalState(AddressLocalState.class);
        if (localState == null) {
            localState = new AddressLocalState();
        }
        String nextStep = localState.getNextStep();
        if (nextStep == null) {
            nextStep = executionContext.getMessage().getText();
        }

        TemplateAddress addressTemplate = addressTemplateMap.getOrDefault(nextStep, addressTemplateMap.get("default"));
        addressTemplate.execute(executionContext, localState);
        localState.setNextStep(addressTemplate.nextStep());
        executionContext.setLocalState(localState);
        executionContext.setGlobalState(BotState.ADDRESS_ALL);
    }

    @Override
    public String commandName() {
        return "Мої адреси";
    }

    @Override
    public BotState state() {
        return BotState.ADDRESS_ALL;
    }

    @Override
    public MainScreen mainScreen() {
        return null;
    }

    static void saveUserAddress(ExecutionContext executionContext, AddressLocalState localState, UserAddress userAddress, UserAddressRepository userAddressRepository) {
        userAddress.setAddressId(localState.getAddressId());
        userAddress.setUserId(executionContext.getUser().getId());
        userAddressRepository.save(userAddress);
        executionContext.getReplyMessageServiceResident().replyMessage("Вітаємо, ви успішно додали своє житло", ReplyButtonsService.newKeyboardButton(List.of(
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
    }
}