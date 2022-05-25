package denis.service;

import denis.FirstTestBotDjek;
import denis.model.TextMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

public class ReplyMessageService {
    private final long chatId;
    private final FirstTestBotDjek bot;

    public ReplyMessageService(long chatId, FirstTestBotDjek bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    public void replyMessage(TextMessage textMessage, ReplyKeyboard replyKeyboard) {
        try {
            bot.execute(
                    SendMessage.builder()
                            .text(textMessage.getValue(textMessage))
                            .chatId(String.valueOf(chatId))
                            .replyMarkup(replyKeyboard)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void replyMessage(String textMessage, ReplyKeyboard replyKeyboard) {
        try {
            bot.execute(
                    SendMessage.builder()
                            .text(textMessage)
                            .chatId(String.valueOf(chatId))
                            .replyMarkup(replyKeyboard)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void replyMessage(TextMessage textMessage, boolean hideButtons) {
        replyMessage(textMessage, new ReplyKeyboardRemove(hideButtons));
    }

    public void replyMessage(String textMessage, boolean hideButtons) {
        replyMessage(textMessage, new ReplyKeyboardRemove(hideButtons));
    }

    public void replyMessage(TextMessage textMessage) {
        replyMessage(textMessage,true);
    }

    public void replyMessage(String textMessage) {
        replyMessage(textMessage,true);
    }
}
