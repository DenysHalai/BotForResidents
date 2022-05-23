package denis;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtons {

    public static InlineKeyboardMarkup inlineKeyboardOne(String name, String call) {
        InlineKeyboardMarkup inlineKeyboardOne = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Ввести адресу вручну");
        inlineKeyboardButton1.setCallbackData("123");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardOne.setKeyboard(rowList);
        return inlineKeyboardOne;
    }

    public static KeyboardButton buttonsNew(String titleButtons, boolean requestContact, boolean requestGeo) {
        KeyboardButton buttonFirst = new KeyboardButton(titleButtons);
        buttonFirst.setRequestContact(requestContact);
        buttonFirst.setRequestLocation(requestGeo);
        return buttonFirst;
    }

    public static KeyboardButton buttonsNew(String titleButtons) {
        return buttonsNew(titleButtons, false, false);
    }
}
