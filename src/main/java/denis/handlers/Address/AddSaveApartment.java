package denis.handlers.Address;

import denis.model.UserAddress;
import denis.repository.UserAddressRepository;
import denis.states.AddressLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class AddSaveApartment implements TemplateAddress {

    private final UserAddressRepository userAddressRepository;

    public AddSaveApartment(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        UserAddress userAddress = new UserAddress();
        userAddress.setApartmentNumber(executionContext.getMessage().getText());
        MainAddress.saveUserAddress(executionContext, localState, userAddress, userAddressRepository);
        executionContext.setLocalState(null);
    }

    @Override
    public String nextStep() {
        return null;
    }

    @Override
    public String commandName() {
        return "Запис данних";
    }
}
