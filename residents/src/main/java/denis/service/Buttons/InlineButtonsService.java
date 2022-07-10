package denis.service.Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineButtonsService {

    public static InlineKeyboardMarkup inlineKeyboardQueryCurrentChat(String name) {
        InlineKeyboardMarkup inlineKeyboardOne = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(name);
        inlineKeyboardButton1.setSwitchInlineQueryCurrentChat("");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardOne.setKeyboard(rowList);
        return inlineKeyboardOne;
    }
}
