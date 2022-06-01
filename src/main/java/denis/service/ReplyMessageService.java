package denis.service;

import denis.FirstTestBotDjek;
import denis.model.TextMessage;
import denis.model.User;
import denis.service.Buttons.ButtonsTemplate;
import denis.service.Buttons.ReplyButtonsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.List;

public class ReplyMessageService {
    private final long chatId;
    private final FirstTestBotDjek bot;
    private final User user;

    public ReplyMessageService(long chatId, FirstTestBotDjek bot, User user) {
        this.chatId = chatId;
        this.bot = bot;
        this.user = user;
    }

    public Message replyMessage(String textMessage, ReplyKeyboard replyKeyboard) {
        try {
            return bot.execute(
                    SendMessage.builder()
                            .text(textMessage)
                            .chatId(String.valueOf(chatId))
                            .replyMarkup(replyKeyboard)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Message replyMessage(TextMessage textMessage, ReplyKeyboard replyKeyboard) {
        return replyMessage(textMessage.getValue(), replyKeyboard);
    }

    public Message replyMessage(TextMessage textMessage, boolean hideButtons) {
        return replyMessage(textMessage, new ReplyKeyboardRemove(hideButtons));
    }

    public Message replyMessage(String textMessage, boolean hideButtons) {
        return replyMessage(textMessage, new ReplyKeyboardRemove(hideButtons));
    }

    public Message replyMessage(TextMessage textMessage) {
        return replyMessage(textMessage, true);
    }

    public Message replyMessage(String textMessage) {
        return replyMessage(textMessage, true);
    }

    public void deleteMessage(Integer messageId) {
        try {
            bot.execute(
                    DeleteMessage
                            .builder()
                            .messageId(messageId)
                            .chatId(String.valueOf(chatId))
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void hideButtons() {
        Message message = replyMessage("...");
        deleteMessage(message.getMessageId());
    }

    public void replyWithMainMenu(TextMessage textMessage) {
        replyMessage(textMessage, ReplyButtonsService.newKeyboardButton(List.of(
                ButtonsTemplate.builder()
                        .title("Мої звернення")
                        .build(),
                ButtonsTemplate.builder()
                        .title("Інструкції по боту")
                        .build())));
    }

    public void replyWithMainMenu() {
        replyWithMainMenu(TextMessage.clickCaseMainMenu);
    }
}
