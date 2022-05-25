package denis.handlers;

import denis.AdressLocalState;
import denis.ExecutionContext;
import denis.googleMapsApi.GeodecodingSample;
import denis.model.*;
import denis.repository.UserAdressRepository;
import denis.service.ReplyButtonsService;
import denis.service.ReplyMessageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AddLocation implements Handler {

    @Autowired
    private UserAdressRepository userAdressRepository;

    @Override
    public void execute(ExecutionContext executionContext) throws IOException {
        AdressLocalState localState = executionContext.getLocalState(AdressLocalState.class);
        ReplyMessageService replyMessageService = executionContext.getReplyMessageService();
        GeodecodingSample param = new GeodecodingSample();
        if (localState == null) {
            localState = new AdressLocalState();
        }
        String nextStep = localState.getNextStep();
        if (("Так, це моя адреса").equals(executionContext.getMessage().getText())) {
            nextStep = "Так, це моя адреса";
        }
        if (nextStep == null) {
            nextStep = "";
        } else if (nextStep.equals("allAdress")) {
            nextStep = executionContext.getMessage().getText();
        }
        if (executionContext.getMessage().hasLocation()) {
            Map<String, Object> location = param.geodecodingSample(executionContext.getMessage().getLocation().getLatitude().toString(),
                    executionContext.getMessage().getLocation().getLongitude().toString());
            localState.setLocation(location);
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) location.get("results");
            Map<String, Object> stringObjectMap = mapList.get(0);
            String fullAdress = (String) stringObjectMap.get("formatted_address");
            findAddressUser(mapList, "country");
            replyMessageService.replyMessage(("Ваш адрес: " + fullAdress), ReplyButtonsService.newButtons("Так, це моя адреса", "Ні, адреса не вірна"));
        } else {
            switch (nextStep) {
                case "Так, це моя адреса": {
                    replyMessageService.replyMessage("Це багатоквартирний будинок?", ReplyButtonsService.newButtons("Так", "Ні"));
                    localState.setNextStep("Багатоквартирний будинок");
                    break;
                }
                case "Багатоквартирний будинок": {
                    if (("Ні").equals(executionContext.getMessage().getText())) {
                        extracted(executionContext, localState, replyMessageService);
                    } else if (("Так").equals(executionContext.getMessage().getText())) {
                        replyMessageService.replyMessage("Введіть номер квартири:");
                        localState.setNextStep("Ввод номеру квартири");
                    } else {
                        replyMessageService.replyMessage("Вкажіть \"Так\" чи \"Ні\":", ReplyButtonsService.newButtons("Так", "Ні"));
                    }
                    break;
                }
                case "Ввод номеру квартири": {
                    extracted(executionContext, localState, replyMessageService);
                    break;
                }
                default:
                    replyMessageService.replyMessage("Натисніть кнопку, щоб поділитися своєю адресою:", ReplyButtonsService.geoButton());
                    executionContext.setGlobalState(BotState.ADRESS_ALL);
            }
        }

        executionContext.setLocalState(localState);
    }

    private void extracted(ExecutionContext executionContext, AdressLocalState localState, ReplyMessageService replyMessageService) {
        UserAdress userAdress = new UserAdress();
        Map<String, Object> location = localState.getLocation();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) location.get("results");
        userAdress.setUserId(executionContext.getMessage().getChatId());
        userAdress.setCountry(findAddressUser(mapList, "country"));
        userAdress.setRegion(findAddressUser(mapList, "administrative_area_level_1"));
        userAdress.setRegionLevel2(findAddressUser(mapList, "administrative_area_level_2"));
        userAdress.setCity(findAddressUser(mapList, "locality"));
        userAdress.setStreet(findAddressUser(mapList, "route"));
        userAdress.setBuildingNumbers(findAddressUser(mapList, "street_number"));
        userAdress.setPostalCode(findAddressUser(mapList, "postal_code"));
        userAdressRepository.save(userAdress);
        replyMessageService.replyMessage(TextMessage.successLocation, ReplyButtonsService.newButtons("До головного меню"));
        localState.setNextStep(null);
        executionContext.setGlobalState(BotState.MAIN_MENU);
    }

    @Override
    public String commandName() {
        return "Додати адресу";
    }

    @Override
    public BotState state() {
        return BotState.ADRESS_ALL;
    }

    public String findAddressUser(List<Map<String, Object>> mapList, String types) {
        Map<String, Object> targetMap = null;
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> stringObjectMap = mapList.get(i);
            List<Map<String, Object>> types1 = (List<Map<String, Object>>) stringObjectMap.get("address_components");
            for (Map<String, Object> objectMap : types1) {
                List<String> types2 = (List<String>) objectMap.get("types");
                for (String values : types2) {
                    if (values.equals(types)) {
                        targetMap = objectMap;
                        break;
                    }
                }
            }

            if (targetMap != null) {
                break;
            }
        }
        if (targetMap == null) {
            return null;
        }
        return (String) targetMap.get("long_name");
    }
}