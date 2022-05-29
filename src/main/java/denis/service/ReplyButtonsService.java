package denis.service;

import denis.model.Icon;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.Collections;
import java.util.List;

public class ReplyButtonsService {

    public static ReplyKeyboardMarkup startButton() {
        ReplyKeyboardMarkup startButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew(Icon.PHONE.get() + " Натисніть щоб поділитися телефоном", true, false)
        ))));
        startButton.setResizeKeyboard(true);
        return startButton;
    }

    public static ReplyKeyboardMarkup geoButton() {
        ReplyKeyboardMarkup geoButton = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew("Поділитися своєю геолокацією", false, true),
                buttonsNew("Вказати адресу вручну", false, false)
        ))));
        geoButton.setResizeKeyboard(true);
        return geoButton;
    }

    public static ReplyKeyboardMarkup mainMenuButtons() {
        ReplyKeyboardMarkup mainMenuButtons = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew("Мої звернення"),
                buttonsNew("Інструкції по боту", false, false)
        ))));
        mainMenuButtons.setResizeKeyboard(true);
        return mainMenuButtons;
    }

    public static ReplyKeyboardMarkup createCase() {
        ReplyKeyboardMarkup createCase = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew("Створити звернення", false, false),
                buttonsNew("Список всіх зверненнь", false, false)
        ))));
        createCase.setResizeKeyboard(true);
        return createCase;
    }

    public static ReplyKeyboardMarkup newButtons(String title) {
        ReplyKeyboardMarkup createCase = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew(title, false, false)
        ))));
        createCase.setResizeKeyboard(true);
        return createCase;
    }

    public static ReplyKeyboardMarkup newWebApp(String title) {
        ReplyKeyboardMarkup createCase = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonWebApp(title)
        ))));
        createCase.setResizeKeyboard(true);
        return createCase;
    }

    public static ReplyKeyboardMarkup newButtons(String title1, String title2) {
        ReplyKeyboardMarkup createCase = new ReplyKeyboardMarkup(Collections.singletonList(new KeyboardRow(List.of(
                buttonsNew(title1, false, false),
                buttonsNew(title2, false, false)
        ))));
        createCase.setResizeKeyboard(true);
        return createCase;
    }

    public static KeyboardButton buttonsNew(String titleButtons, boolean requestContact, boolean requestGeo) {
        KeyboardButton buttonFirst = new KeyboardButton();
        buttonFirst.setText(titleButtons);
        buttonFirst.setRequestContact(requestContact);
        buttonFirst.setRequestLocation(requestGeo);
        return buttonFirst;
    }

    public static KeyboardButton buttonWebApp(String titleButtons) {
        KeyboardButton buttonFirst = new KeyboardButton();
        buttonFirst.setText(titleButtons);
        buttonFirst.setWebApp(new WebAppInfo("https://bot-vue.vercel.app/"));
        return buttonFirst;
    }

    public static KeyboardButton buttonsNew(String titleButtons) {
        return buttonsNew(titleButtons, false, false);
    }
}