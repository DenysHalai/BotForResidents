package denis.handlers.Address;

import denis.model.UserAddress;
import denis.repository.UserAddressRepository;
import denis.states.AddressLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class AddApartment implements TemplateAddress {

    private final UserAddressRepository userAddressRepository;
    private final ThreadLocal<String> newLocal = new ThreadLocal<>();

    public AddApartment(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        if ("Так".equals(executionContext.getMessage().getText())) {
            executionContext.getReplyMessageServiceResident().replyMessage("Введіть номер квартири:");
            newLocal.set("Запис данних");
        } else {
            UserAddress userAddress = new UserAddress();
            MainAddress.saveUserAddress(executionContext, localState, userAddress, userAddressRepository);
            newLocal.set(null);
        }
    }

    @Override
    public String nextStep() {
        String s = newLocal.get();
        newLocal.remove();
        return s;
    }

    @Override
    public String commandName() {
        return "Обрав тип будинку";
    }
}
