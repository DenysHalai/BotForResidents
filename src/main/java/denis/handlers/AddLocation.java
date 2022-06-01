package denis.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import denis.model.LocationData;
import denis.model.TextMessage;
import denis.model.UserAddress;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.FindAddressInDataBaseService;
import denis.states.AddressLocalState;
import denis.states.BotState;
import denis.states.ExecutionContext;
import denis.googleMapsApi.GeodecodingSample;
import denis.repository.UserAddressRepository;
import denis.service.Buttons.ReplyButtonsService;
import denis.service.ReplyMessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AddLocation implements Handler {

    private UserAddressRepository userAddressRepository;
    private final FindAddressInDataBaseService findAddressInDataBaseService;

    public AddLocation(UserAddressRepository userAddressRepository, FindAddressInDataBaseService findAddressInDataBaseService) {
        this.userAddressRepository = userAddressRepository;
        this.findAddressInDataBaseService = findAddressInDataBaseService;
    }

    @Override
    public void execute(ExecutionContext executionContext) throws IOException {
        AddressLocalState localState = executionContext.getLocalState(AddressLocalState.class);
        ReplyMessageService replyMessageService = executionContext.getReplyMessageService();
        GeodecodingSample param = new GeodecodingSample();
        if (localState == null) {
            localState = new AddressLocalState();
        }
        String nextStep = localState.getNextStep();
        if (("Так, це моя адреса").equals(executionContext.getMessage().getText())) {
            nextStep = "Так, це моя адреса";
        } else if (("Вказати адресу вручну").equals(executionContext.getMessage().getText())) {
            nextStep = "Вказати адресу вручну";
        } else if (("Додати адресу").equals(executionContext.getMessage().getWebAppData().getButtonText())) {
            nextStep = "Додати адресу - WebApp";
        }
        if (nextStep == null) {
            nextStep = "";
        } else if (nextStep.equals("allAddress")) {
            nextStep = executionContext.getMessage().getText();
        }
        if (executionContext.getMessage().hasLocation()) {
            LocationData location = param.geodecodingSample(executionContext.getMessage().getLocation().getLatitude().toString(),
                    executionContext.getMessage().getLocation().getLongitude().toString());
            localState.setLocation(location);
            replyMessageService.replyMessage(("Ваш адрес: " + location.getCityType() + " " + location.getCity() + ", " + location.getStreetType() + " " + location.getStreet() + ", " + location.getNumber()),
                    ReplyButtonsService.newKeyboardButton(
                            List.of(ButtonsTemplate.builder()
                                            .title("Так, це моя адреса")
                                            .build(),
                                    ButtonsTemplate.builder()
                                            .title("Ні, адреса не вірна")
                                            .build())));
            executionContext.setLocalState(localState);
            return;
        }
        switch (nextStep) {
/*                case "Так, це моя адреса": {
                    replyMessageService.replyMessage("Це багатоквартирний будинок?", ReplyButtonsService.newButtons("Так", "Ні"));
                    localState.setNextStep("Багатоквартирний будинок");
                    break;
                }
                case "Ні, адреса не вірна": {
                    replyMessageService.replyMessage("Натисніть кнопку, щоб поділитися своєю адресою:", ReplyButtonsService.geoButton());
                    executionContext.setGlobalState(BotState.ADDRESS_ALL);
                    localState.setNextStep(null);
                    localState.setLocation(null);
                }
                case "Багатоквартирний будинок": {
                    if (("Ні").equals(executionContext.getMessage().getText())) {
                        extracted(executionContext, localState, replyMessageService);
                    } else if (("Так").equals(executionContext.getMessage().getText())) {
                        replyMessageService.replyMessage("Введіть номер квартири:");
                        localState.setNextStep("Ввод номеру квартири");
                    } else {
                        replyMessageService.replyMessage("Вкажіть \"Так\" чи \"Ні\":", ReplyButtonsService.newButtons("Так", "Ні"));*/
/*                    }
                    break;
                }
                case "Ввод номеру квартири": {
                    UserAddress userAddress = new UserAddress();
                    List<Map<String, Object>> mapList = (List<Map<String, Object>>) location.get("results");
                    userAdress.setUserId(executionContext.getMessage().getChatId());
                    userAdress.setFlatNumbers(executionContext.getMessage().getText());
                    userAdress.setCountry(findAddressUser(mapList, "country"));
                    userAdress.setRegion(findAddressUser(mapList, "administrative_area_level_1"));
                    userAdress.setRegionLevel2(findAddressUser(mapList, "administrative_area_level_2"));
                    userAdress.setCity(findAddressUser(mapList, "locality"));
                    userAdress.setStreet(findAddressUser(mapList, "route"));
                    userAdress.setBuildingNumbers(findAddressUser(mapList, "street_number"));
                    userAdress.setPostalCode(findAddressUser(mapList, "postal_code"));
                    userAdressRepository.save(userAdress);*//*
                    executionContext.setGlobalState(BotState.MAIN_MENU);
                    replyMessageService.replyMessage(TextMessage.successLocation, ReplyButtonsService.newWebAppAndButtons("Мої звернення","https://bot-vue.vercel.app/allcases?userId=" + executionContext.getUser().getId(),"Інструкції по боту"));
                    break;
                }
                case "Вказати адресу вручну": {
                    replyMessageService.hideButtons();
                    replyMessageService.replyMessage("Натисніть на кнопку нижче, щоб почати вводити адресу:", InlineButtonsService.inlineKeyboardQueryCurrentChat("Ввести адресу"));
                    localState.setNextStep("Вказав адресу вручну");
                    break;
                }
                case "Вказав адресу вручну": {
                    replyMessageService.replyMessage("Це багатоквартирний будинок?", ReplyButtonsService.newButtons("Так", "Ні"));
                    localState.setNextStep("Багатоквартирний будинок");
                }*/
            case "Додати адресу - WebApp":
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> map = mapper.readValue(executionContext.getMessage().getWebAppData().getData(), Map.class);
                findAddressInDataBaseService.tryFindAddress(map.get("city"), map.get("street"), map.get("number"), executionContext.getUser().getId(), map.get("numberApart"));
                replyMessageService.replyWithMainMenu();
                executionContext.setGlobalState(BotState.MAIN_MENU);
                break;
            default:
                replyMessageService.replyMessage("Натисніть кнопку, щоб поділитися своєю адресою:", ReplyButtonsService.newKeyboardButton(
                        ButtonsTemplate.builder()
                                .title("Поділитися своєю геолокацією")
                                .requestLocation(true)
                                .build()));
                executionContext.setGlobalState(BotState.ADDRESS_ALL);
        }

        executionContext.setLocalState(localState);
    }

    private void extracted(ExecutionContext executionContext, AddressLocalState localState, ReplyMessageService replyMessageService) {
        UserAddress userAddress = new UserAddress();/*
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
        replyMessageService.replyMessage(TextMessage.successLocation, ReplyButtonsService.newButtons("Мої звернення", "Інструкції по боту"));
        executionContext.setGlobalState(BotState.MAIN_MENU);*/
    }

    @Override
    public String commandName() {
        return "Додати адресу";
    }

    @Override
    public BotState state() {
        return BotState.ADDRESS_ALL;
    }

    /*public String findAddressUser(List<Map<String, Object>> mapList, String types) {
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
    }*/
}