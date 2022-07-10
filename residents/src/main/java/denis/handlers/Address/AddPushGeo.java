package denis.handlers.Address;

import denis.googleMapsApi.GeodecodingSample;
import denis.model.LocationData;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import denis.states.AddressLocalState;
import denis.states.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddPushGeo implements TemplateAddress {
    @Override
    public void execute(ExecutionContext executionContext, AddressLocalState localState) {
        try {
            GeodecodingSample param = new GeodecodingSample();
            LocationData location = param.geodecodingSample(executionContext.getMessage().getLocation().getLatitude().toString(),
                    executionContext.getMessage().getLocation().getLongitude().toString());
            localState.setLocation(location);
            executionContext.getReplyMessageServiceResident()
                    .replyMessage(("Ваша адреса: " + location.getCityType() + " " + location.getCity() + ", "
                                    + location.getStreetType() + " " + location.getStreet() + ", " + location.getNumber()),
                    ReplyButtonsService.newKeyboardButton(
                            List.of(ButtonsTemplate.builder()
                                            .title("Так, це моя адреса")
                                            .build(),
                                    ButtonsTemplate.builder()
                                            .title("Ні, адреса не вірна")
                                            .build())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String nextStep() {
        return "Підтверждення адреси";
    }

    @Override
    public String commandName() {
        return "Поділитися своєю геолокацією";
    }
}
