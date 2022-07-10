package denis.service.Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReplyButtonsService {

    public static KeyboardButton buttonsNew(ButtonsTemplate template) {
        KeyboardButton buttonFirst = new KeyboardButton();
        buttonFirst.setText(template.getTitle());
        buttonFirst.setRequestContact(template.isRequestContact());
        buttonFirst.setRequestLocation(template.isRequestLocation());
        if (template.getWebAppUrl() != null) {
            buttonFirst.setWebApp(new WebAppInfo(template.getWebAppUrl()));
        }
        return buttonFirst;
    }

    public static List<KeyboardButton> buttonsNew(List<ButtonsTemplate> template) {
        return template.stream().map(ReplyButtonsService::buttonsNew).collect(Collectors.toList());
    }

    public static ReplyKeyboardMarkup newKeyboardButton(ButtonsTemplate template) {
        ReplyKeyboardMarkup createCase = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew(template)
        ))));
        createCase.setResizeKeyboard(true);
        return createCase;
    }

    public static ReplyKeyboardMarkup newKeyboardButton(List<ButtonsTemplate> template) {
        ReplyKeyboardMarkup createCase = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(
                buttonsNew(template)
        )));
        createCase.setResizeKeyboard(true);
        return createCase;
    }

}